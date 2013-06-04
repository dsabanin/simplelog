(defproject wildbit/simplelog "1.0.13"
  :description "SimpleLog is a logging tool for Beanstalk's services"
  :url "http://beanstalkapp.com"
  :license {:name "Wildbit Proprietary License"
            :url "http://wildbit.com"}
  :author "Dmitry Sabanin <sdmitry@gmail.com>"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-statsd "0.3.2"]
                 [clj-stacktrace "0.2.5"]]
  :plugins [[s3-wagon-private "1.1.2"]]
  :repositories {"wildbit" {:url "s3p://clojure-jars/releases/" :creds :gpg}})
