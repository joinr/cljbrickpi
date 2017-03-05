(ns cljbrickpi.sensor
  (:require [cljbrickpi.util :refer get!])
  (:import [com.ergotech.brickpi.sensors
            Sensor SensorPort SensorType TouchSensor] 
           [com.ergotech.brickpi BrickPiCommunications]))


(def sensors
  {:ultrasonic SensorType/Ultrasonic
   :ultrasonicss  SensorType/UltrasonicSS
   :raw SensorType/Raw
   :touch :touch}
  )

(def ports
  {:s1 SensorPort/S1
   :s2 SensorPort/S2
   :s3 SensorPort/S3
   :s4 SensorPort/S4
   })

(defn ->sensor [stype]
  (let [res(get sensors stype)]
    (case res
      :touch (TouchSensor.)
      (Sensor. (get! sensors type)))))
