# REDIS configuration
export VIP_NS_REDIS_MAX_IDLE=10
export VIP_NS_REDIS_MAX_TOTAL=40
export VIP_NS_REDIS_MAX_WAIT=1000
export VIP_NS_REDIS_MIN_EVICATABLE_IDLE_TIME=1000
export VIP_NS_REDIS_MIN_IDLE=2
export VIP_NS_REDIS_TIME_BETWEEN_EVICATION_RUN=100
export VIP_NS_REDIS_HOSTS=""

# Zookeeper configuration
export VIP_NS_ZK_HOST=
export VIP_NS_ZK_CLIENT_TMO=2000

# KAFKA configuration
export VIP_NS_KAFKA_BROKERS=

# UMC-Admin configuration
export VIP_NS_CFG_URL=http://ip:port/ns/getConfig?ip=