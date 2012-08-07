(ns misaki.test.compiler.config
  (:use [misaki.compiler.default config]
        misaki.test.compiler.common
        [clj-time.core :only [date-time year month day]]
        clojure.test)
  (:require [clojure.java.io :as io]))

;;; get-date-from-file
;;;;(deftest* get-date-from-file-test
;;;;  ; test config: #"(\d{4})[.](\d{1,2})[.](\d{1,2})[-_](.+)$"
;;;;  (testing "valid date"
;;;;    (let [date (get-date-from-file (io/file "2000.11.22-dummy.clj"))]
;;;;      (are [x y] (= x y)
;;;;        2000 (year date)
;;;;        11   (month date)
;;;;        22   (day date))))
;;;;
;;;;  (testing "invalid date"
;;;;    (are [filename] (nil? (get-date-from-file (io/file filename)))
;;;;      "2000.11.xx.dummy.clj"
;;;;      "2000.xx.22-dummy.clj"
;;;;      "xxxx.11.22-dummy.clj"
;;;;      "2000.11.dummy.clj"
;;;;      "2000.dummy.clj"
;;;;      "dummy.clj"
;;;;      ""
;;;;      nil)))
;;;;
;;;;;;; remove-date-from-name
;;;;(deftest* remote-date-from-name-test
;;;;  (is (= "dummy.clj" (remove-date-from-name "2000.11.22-dummy.clj"))))

;;; make-post-output-filename
(deftest* make-post-output-filename-test
  ; test config: "{{year}}-{{month}}/{{filename}}"
  (testing "filename with date"
    (let [file (io/file "2000.11.22-dummy.html.clj")]
      (is (= "2000-11/dummy.html" (make-post-output-filename file)))))

  (testing "filename without date"
    (are [x y] (= x (make-post-output-filename (io/file y)))
      "-/foo.html"    "foo.html.clj"
      "-/01.foo.html" "01.foo.html.clj"
      "-/01.02.foo.html" "01.02.foo.html.clj")))

;;; make-template-output-filename
(deftest* make-template-output-filename-test
  (let [tmpl-name "template.test.html.clj"
        file (io/file (str (:template-dir *config*) tmpl-name))
        name1 (make-template-output-filename file)
        name2 (make-template-output-filename tmpl-name)]
    (is (= name1 name2))))

