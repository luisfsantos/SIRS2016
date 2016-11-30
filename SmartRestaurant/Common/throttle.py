from rest_framework.throttling import AnonRateThrottle

class BurstRegisterThrottle(AnonRateThrottle):
    scope = "burst"
    rate = "5/hour"

class DailyRegisterThrottle(AnonRateThrottle):
    scope = "sustained"
    rate = "20/day"

class BurstLoginThrottle(AnonRateThrottle):
    scope = "burst"
    rate = "10/min"

class DailyLoginThrottle(AnonRateThrottle):
    scope = "sustained"
    rate = "100/day"