(ns simple-web-server.core
  (:require 
    [org.httpkit.server :refer [run-server]]
    [clojure.data.json :as json]
    [ring.middleware.cors :refer [wrap-cors]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
    [compojure.route :refer [not-found]]
    [compojure.core :refer [defroutes GET]])
  (:gen-class))

(defonce server (atom nil))


(defn simple-get [req]
  (println "Got request:", (:params req))
  (Thread/sleep 1000)
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-str {:key "value"})})


(defroutes all-routes
  (GET "/simple-get" [] simple-get)
  (not-found "<p>Page not found.</p>")) ;; all other, return 404

(def app
  (-> all-routes
    (wrap-defaults site-defaults)
    (wrap-cors
      :access-control-allow-origin  [#".*"]
      :access-control-allow-headers ["Content-Type" "Authorization" "Accept" "Origin"]
      :access-control-allow-methods [:get :put :post :delete :options])))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& args]
  ; The #' is useful when you want to hot-reload code
  (reset! server (run-server #'app {:port 8080})))


(comment
  all-routes
  (json/write-str {:name "sainadh" :age 27})
  (stop-server)
  (-main))
  
