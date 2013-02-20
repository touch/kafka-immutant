(ns immutant.init
  (:import  [kafka.server KafkaConfig KafkaServer]
            [java.util    Properties])
  (:require [immutant.daemons :as daemons]))

(def config  (KafkaConfig. (doto (Properties.)
  (.setProperty "port"       , "9090");
  (.setProperty "brokerid"   , "1")
  (.setProperty "log.dir"    , "/var/log/kafka/")
  (.setProperty "zk.connect" , "zookeeper"))))

(def server (KafkaServer. config))


;; start a daemon
(daemons/daemonize "kafka" #(.startup server) #(do (.shutdown server) (.awaitShutdown server)))
