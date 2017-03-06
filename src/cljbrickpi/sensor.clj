(ns cljbrickpi.sensor
  (:require [cljbrickpi.util :refer [get!]])
  (:import [com.ergotech.brickpi.sensors
            Sensor SensorPort SensorType TouchSensor
            EV3TouchSensor] 
           [com.ergotech.brickpi BrickPiCommunications]))

(def sensors
  {:ultrasonic SensorType/Ultrasonic
   :ultrasonicss  SensorType/UltrasonicSS
   :raw SensorType/Raw
   :touch :touch
   :ev3touch :ev3touch
   ;;Continuous measurement, distance, cm
   :ev3-ultrasound-cm SensorType/EV3UltrasoundCm
   ;;Continuous measurement, distance, in
   :ev3-ultrasound-in SensorType/EV3UltrasoundIn
   ;;http://www.ev3dev.org/docs/sensors/lego-ev3-color-sensor/ 
   ;;Reflected light intensity (0 to 100)
   :ev3-color-intensity SensorType/EV3ColorIntensity
    
    ;;Angle
    :ev3-gyro-angle SensorType/EV3GyroAngle
    ;;Rotational Speed
    :ev3-gyro-speed SensorType/EV3GyroSpeed
    ;;Raw sensor value ???
    :ev3-gyro-raw SensorType/EV3GyroRaw
    ;;Angle and Rotational Speed?
    :ev3-gyro-angle-speed SensorType/EV3GyroAngleSpeed
     ;;Calibration ???
    :ev3-gyro-calibration SensorType/EV3GyroCalibration
    ;; Mode information is here:
    ;;http://www.ev3dev.org/docs/sensors/lego-ev3-infrared-sensor
   
    ;;Proximity, 0 to 100
    :ev3-infrared-proximity SensorType/EV3IRProximity
    ;;IR Seek, -25 (far left) to 25 (far right)
    :ev3-infrared-seek SensorType/EV3IRSeek
    ;;IR Remote Control, 0 - 11
    :ev3-infrared-remote SensorType/EV3IRRemote}
  )

(def ports
  {:s1 SensorPort/S1
   :s2 SensorPort/S2
   :s3 SensorPort/S3
   :s4 SensorPort/S4
   })

(defn ^SensorPort sensor-port [x]
  (get! ports x))


(defn sensor? [x]
  (isa? (class x)
        com.ergotech.brickpi.sensors.Sensor))

(defn ->sensor [stype]
  (if (sensor? stype) stype
      (let [res (get sensors stype)]
        (case res
          :touch (TouchSensor.)
          :ev3touch (EV3TouchSensor.)
          (Sensor. res)))))

;;small problem...
;;the sensortypes are pre-baked apparently.
;;because they're enums.
(defn sensor-value [^Sensor s]
  (.getValue s))

;;adding EV3 IR sensor types
;;TYPE_SENSOR_EV3_INFRARED_M2
