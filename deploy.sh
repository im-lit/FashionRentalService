echo "Building app..."
./mvnw clean package

echo "Deploy files to server..."
scp -r -i ~/Onedrive/Desktop/SWP490/FashionRentalService target/fashionrental.jar root@134.209.111.144:/SWP490/FashionRental_BE/

ssh -i ~/Onedrive/Desktop/long root@134.209.111.144 <<EOF
pid=\$(sudo lsof -t -i :8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
cd /var/www/demo-swp-be
java -jar demo-swp.jar
EOF
exit

echo "Done!"