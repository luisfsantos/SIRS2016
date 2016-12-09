from rest_framework.throttling import AnonRateThrottle
from rest_framework.throttling import UserRateThrottle

class BurstRegisterThrottle(AnonRateThrottle):
    scope = "burst"
    rate = "5/hour"

class DailyRegisterThrottle(AnonRateThrottle):
    scope = "sustained"
    rate = "20/day"

class BurstLoginThrottle(AnonRateThrottle):
    scope = "burst"
    rate = "5/s"

class DailyLoginThrottle(AnonRateThrottle):
    scope = "sustained"
    rate = "100/day"

class DailyAPIThrottle(UserRateThrottle):
    scope = "sustained"
    rate = "500/day"

class BurstAPIThrottle(UserRateThrottle):
    scope = "burst"
    rate = "20/min"

class OrderAPIThrottle(UserRateThrottle):
    scope = "burst"
    rate = "3/sec"
