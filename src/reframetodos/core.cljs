(ns reframetodos.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [reframetodos.handlers :as handlers]
            [reframetodos.views :refer [Footer-Filters List-Todo Add-Todo-List]]))

(enable-console-print!)


(defn home []
  [:div
   [Add-Todo-List]
   [Footer-Filters]
   ])

(defn main []
  (dispatch-sync [:initialize])
  (reagent/render [home] (js/document.getElementById "app")))

(main)
