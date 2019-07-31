source ./values.sh
source ./export_cluster_config.sh
source ./cloud_login.sh
bash build_and_push.sh
bash create_secrets.sh
bash deploy.sh