(ns gts-move-on.core
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]))

(def mapping (json/read-str (slurp (io/resource "gitorious-gitlab-migrated.json"))))

(html/deftemplate template "public/index.html"
  [gitlab-path]
  [:h1] (html/content (if gitlab-path "Moved" "Hold on..."))
  [:p.generic-message] #(when (not gitlab-path) %)
  [:p.repo-message] #(when gitlab-path %)
  [:a.gitlab-link] (html/do->
                     (html/content (str "https://gitlab.com/" gitlab-path))
                     (html/set-attr :href (str "https://gitlab.com/" gitlab-path))))

(defn repo-handler [project repository]
  (if-let [gitlab-path (get mapping (str project "/" repository))]
    (template gitlab-path)))

(defroutes app
  (GET "/" [] (template nil))
  (GET "/:project/:repository*" [project repository] (repo-handler project repository))
  (route/not-found (template nil)))

(def handler
  (-> app
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))
