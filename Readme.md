# -> Customer Add
# -> Customer Get
# -> Inventory Get products
# -> Order Create
# -> Order Get
# -> Payment Create
# -> Payment Get
# -> Order Update

.\kafka-topics.bat --create --topic orders --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

.\kafka-topics.bat --list --bootstrap-server localhost:9092<img width="599" height="28" alt="image" src="https://github.com/user-attachments/assets/41859128-4e00-4aa1-983c-46096d27fc19" />
