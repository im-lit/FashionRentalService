 echo "Building app..."
 ./mvnw clean package

echo "Deploy files to server..."
scp -r -i ./fashion target/fashionrental.jar root@167.71.221.112:/root/

ssh -i ./fashion root@167.71.221.112 <<EOF
pid=\$(sudo lsof -t -i :8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
java -jar fashionrental.jar
EOF
exit

echo "Done!"