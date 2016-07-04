(ns reframetodos.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reagent.core :as r]))

(def click-value (r/atom ""))

(defn List-Todo [todos1]
   (fn []
     [:div
      [:table
       [:tbody
        (for [i (range 0 (count @todos1))]
          ^{:key i}
          [:tr
           [:td (get-in @todos1 [i :task])]
           [:td 
            [:button {:on-click #(dispatch [:delete 
                                            (get-in @todos1 [i :id])])} "X"]]])]]
          [:p (str @todos1)]]))


(defn Add-Todo-List []
  (let [text (atom "")]
    (fn []
      [:div
       [:span
        [:input {:type "text"
                 :placeholder "Enter todo"
                 :on-change #(reset! text (-> % .-target .-value))}]
        [:button {:on-click #(List-Todo (dispatch [:add text]))} "Add"]]])))


(defn Footer-Filters []
  [:div
   [:span
    [:button {:value @click-value
              :on-click #(List-Todo (dispatch [:all]))} "All"]
    [:button {:value @click-value
              :on-click #(List-Todo (dispatch [:active]))} "Active"]
    [:button {:value @click-value
              :on-click #(List-Todo (dispatch [:complete]))} "Complete"]
    [:button "ClearComplete"]]])

#_(defn Footer-Filters []
  (let [todos (subscribe [:todos])]
    (case @click-value
      "All" (List-Todo todos)
      "Active" (List-Todo (dispatch [:active]))
      "Complete" (List-Todo (dispatch [:complete]))
      (List-Todo todos))))


