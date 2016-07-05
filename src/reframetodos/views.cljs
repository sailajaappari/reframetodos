(ns reframetodos.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reagent.core :as r]
            [reframetodos.functions :as f]))

(def click-value (r/atom ""))

(defn List-Todo []
  (let [todos (subscribe [:temptodos])]
    (fn []
      [:div
       [:table
        [:tbody
         (for [i (range 0 (count @todos))]
           ^{:key i}
           [:tr
            [:td (get-in @todos [i :task])]
            (if (get-in @todos [i :active])
              [:td "P"]
              [:td "D"])
            [:td            
             (if (= (get-in @todos [i :active]) true)
               
               [:button {:on-click 
                         #(do 
                            (dispatch [:change-active-state i])
                            (dispatch [:todos-to-temp]))} 
                "Mark Done"]
               
               [:button {:on-click 
                         #(do 
                            (dispatch [:change-active-state i])
                            (dispatch [:todos-to-temp]))} 
                "Mark Pending"])]
            [:td 
             [:button {:on-click #(do 
                                   (dispatch [:delete (get-in @todos [i :id])])
                                   (dispatch [:todos-to-temp]))} "X"]]])]]
       [:p (str @todos)]])))


 (defn Add-Todo-List []
   (let [text (atom "")]
     (fn []
       [:div
        [:span
         [:input {:type "text"
                  :placeholder "Enter todo"
                  :on-change #(reset! text (-> % .-target .-value))}]
         [:button {:on-click #(do 
                                (dispatch [:add @text])
                                (dispatch [:todos-to-temp]))} "Add"]]])))


(defn footer []
  (fn []
    [:div
     [:span
      [:span "Items Left: " (f/ActiveTodosCount (subscribe [:todos]))]
      [:button {:on-click #(dispatch [:all])} "All"]
      
      [:button {:on-click #(dispatch [:active])} "Active"]
        
      [:button {:on-click #(dispatch [:complete])} "Complete"]
        
      [:button {:on-click #(dispatch [:clear-complete])} "ClearComplete"]]]))

 #_(defn filters []
     (let [todos1 (subscribe [:todos])
           active (subscribe [:active-todos])
           complete (subscribe [:complete-todos]
           all (subscribe [:all-todos]))]
       (case @click-value
         "All" (List-Todo all)
         "Active" (List-Todo active)
         "Complete" (List-Todo complete)
         (List-Todo todos1))))
