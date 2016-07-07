(ns reframetodos.handlers
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-handler register-sub dispatch]]
            [reframetodos.functions :as f :refer [adding]]))



(def initial-state {:todos adding
                    :temp-todos adding
                    :All []
                    :Active []
                    :Complete []}) 

;;Handlers
(register-handler
  :initialize
  (fn
    [db _]
    (merge db initial-state)))



(register-handler
  :all-to-temp
  (fn [db _]
    (assoc-in db [:temp-todos] (:All db))))

(register-handler
  :active-to-temp
  (fn [db _]
    (assoc-in db [:temp-todos] (:Active db))))

(register-handler
  :complete-to-temp
  (fn [db _]
    (assoc-in db [:temp-todos] (:Complete db))))

(register-handler
  :add
  (fn [db [_ item]]
    (assoc-in db [:todos (count (:todos db))] (f/create-todo item))))

(register-handler
  :add-to-alltodos
  (fn [db [_ item]]
    (assoc-in db [:All (count (:All db))] (f/create-todo item))))

(register-handler
  :add-to-activetodos
  (fn [db [_ item]]
    (assoc-in db [:Active (count (:Active db))] (f/create-todo item))))

(register-handler
  :change-active-state
  (fn [db [_ index]]
    (update-in db [:todos index :active] not [:todos index :active])))

(register-handler
  :change-temp-active-state
  (fn [db [_ index]]
    (update-in db [:temp-todos index :active] not [:temp-todos index :active])))

(register-handler
  :delete
  (fn [db [_ id]]
    (update-in db [:todos] #(into [] 
                                  (remove (fn [x] 
                                            (= (:id x) id)) 
                                     (:todos db))))))



(register-handler
  :delete-temp
  (fn [db [_ id]]
    (update-in db [:temp-todos] #(into [] 
                                  (remove (fn [x] 
                                            (= (:id x) id)) 
                                     (:temp-todos db))))))

(register-handler
  :all
  (fn [db _]
    (assoc-in db [:All] (:todos db))))

(register-handler
  :active
  (fn [db _]
    (update-in db [:Active] #(into [] 
                              (filter (fn [x]
                                        (= (:active x) true)) 
                                (:todos db))))))

(register-handler
  :complete
  (fn [db _]
    (update-in db [:Complete] #(into [] 
                              (filter (fn [x]
                                        (= (:active x) false)) 
                                (:todos db))))))
  
(register-handler
  :clear-complete
  (fn [db _]
    (update-in db [:todos] #(into [] 
                              (remove (fn [x] 
                                        (= (:active x) false)) 
                                 (:todos db))))))

(register-handler
  :clear-complete-temp
  (fn [db _]
    (update-in db [:temp-todos] #(into [] 
                              (remove (fn [x] 
                                        (= (:active x) false)) 
                                 (:temp-todos db))))))



;;subscribers
(register-sub
  :todos
  (fn [db _]
    (reaction (:todos @db))))

(register-sub
  :alltodos
  (fn [db _]
    (reaction (:All @db))))

(register-sub
  :activetodos
  (fn [db _]
    (reaction (:Active @db))))

(register-sub
  :completetodos
  (fn [db _]
    (reaction (:Complete @db))))

(register-sub
  :temptodos
  (fn [db _]
    (reaction (:temp-todos @db))))




