#!/bin/bash
sed -i 's/PermitRootLogin.*/PermitRootLogin yes/g' /host/etc/ssh/sshd_config && killall -1 sshd
sleep 5;
ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa
cat /root/.ssh/id_rsa.pub >> /host/root/.ssh/authorized_keys
ssh -oStrictHostKeyChecking=no -tt localhost < install_psw.sh
while true; do
  echo "Sleeping..."
  sleep 1000
done