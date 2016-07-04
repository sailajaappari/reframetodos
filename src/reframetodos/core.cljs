(ns reframetodos.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [reframetodos.handlers :as handlers]
            [reframetodos.views :refer [List-Todo]]))

(enable-console-print!)


(defn home []
  [:div
   [List-Todo]])

(defn main []
  (dispatch-sync [:initialize])
  (reagent/render [home] (js/document.getElementById "app")))

(main)
