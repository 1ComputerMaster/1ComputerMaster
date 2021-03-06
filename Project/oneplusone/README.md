# **1bool1**
<div align="center">

![png_1](./exec/λ‘κ³ /λ‘κ³ .png)

</div>


## π νλ‘μ νΈ κ°μ
---
### **λͺ©ν**

- νΈμμ  μ΄μ©λ₯ μ΄ λμμ§λ μμ¦ νΈμμ  λ μνΌ λ° νΈμμ  μ΄λ²€νΈ μνμ μ κ³΅νλ μΉ νλ‘μ νΈ

### **κΈ°λ ν¨κ³Ό**

- μ¬μ©μλ€μ ν΄λΉ μλΉμ€λ₯Ό μ΄μ©νμ¬ μ΅μ μ μ νμ ν  μ μκ² μ΅μ μ κ°κ²©μ μνμ μ°Ύκ³  νΈμμ μμλ§ λ§λ€ μ μλ νΉλ³ν μμλ€λ‘ λ°₯μ μ½κ² μ­μ·¨ ν  μ μμ΅λλ€.  

### **μ μ μμ**

### Anonymous communication 

- μ΅λͺμ μμ¬μν΅ - μ¬λλ€κ³Όμ κΈ°λ³Έμ μΈ μμ¬μν΅μ μ΅λͺμΌλ‘ ν  μ μμ΄ λΆλ΄μ λ΄λ € λκ³  μν΅ ν  μ μμ΅λλ€.

### Recipe

- λ μνΌ - μ¬μ©μλ€κ° νΈμμ  μμλ€λ‘λ§ κ΅¬μ±λ μμμ μ μ ν  μ μμ΅λλ€.

### Easy login

- μ¬μ΄ λ‘κ·ΈμΈ - μΉ΄μΉ΄μ€ν‘ Login APIλ₯Ό μ΄μ©νμ¬ νμ κ°μ μ μ°¨κ° νμ μμ΄ λ‘κ·ΈμΈ κ°λ₯ν©λλ€.

### Event

- μ΄λ²€νΈ - μ΄λ²€νΈλ₯Ό ν¬λ‘€λ§ λ°μ΄ν°λ‘ μμ§νμ¬ κ°κ°μ νΈμμ  λ§λ€ μΈμΌνλ λ¬Όν λ° μ΄λ²€νΈ μνμ λλ¬ λ³Ό μ μμ΅λλ€. 

### Notification

- μλ - μλ κΈ°λ₯μ ν΅νμ¬ μ¬λλ€μ΄ μΉ΄μΉ΄μ€ν‘ λ‘κ·ΈμΈμ΄ μ μμ μΌλ‘ μλ£ λ¨μ μ μ μμ΅λλ€.


# β κΈ°ν
## π£ERD
---
![png_1](./exec/ERD/ERD.png)

## πWireFrame
---
![png_2](./exec/μμ΄μ΄νλ μ/μμ΄μ΄νλ μ.png)

<br/><br/>

# π­λ°°ν¬
---
## π¨βπ»Client
---
```
$ cd FE
$ npm install
$ npm run serve
```


## π©βπ»Server
---
### 1. Java μ€μΉ
```
$ sudo apt-get install openjdk-8-jre
$ sudo apt-get install openjdk-8-jdk
```
### 2. Npm μ€μΉ
```
$ sudo apt install npm
```

### 3. Docker μ€μΉ
```
$ sudo apt install apt-transport-https ca-certificates curl gnupg-agent software-properties-common // λ€μ ν¨ν€μ§λ€μ μ€μΉ
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add β // Dockerμ κ³΅μ GPGν€λ₯Ό μΆκ°νλ€.
$ sudo add-apt-repository "deb [arch=amd64] 
https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" // stable repositoryλ₯Ό μΈννκΈ° μν λͺλ Ήμ΄
$ sudo apt install docker-ce docker-ce-cli containerd.io // κ°μ₯ μ΅μ  λ²μ μ Docker μμ§μ μ€μΉνλ€.
$ docker -v // λμ»€ μ€μΉ νμΈ
```

### 4. Docker MariaDB install
```
$ sudo docker run --name DBμ΄λ¦ -p 3306:3306 -e MYSQL_ROOT_PASSWORD=λΉλ°λ²νΈ - d mariadb // Dockerλ‘ Mariadb μ€μΉ λ° μ€ν
```
### 5. Gradle install
```
$ apt-get update
$ apt-get install unzip wget
$ wget https://downloads.gradle-dn.com/distributions/gradle-6.7-bin.zip //gradle 6.7 μ€μΉ
$ unzip gradle-6.7-bin.zip -d /opt
$ In -s /opt/gradle-6.7 /opt/gradle
$ vi /etc/profile.d/gradle.sh
```
-  
  ### 5-1. gradle μ€μ 
  ```
  #/bin/bash
  export GRADLE_HOME=/opt/gradle
  export PATH=/opt/gradle/bin:${PATH}
  
  $ gradle -v //gradle μ€μΉ νμΈ
  ```
### 6. νλ‘μ νΈ μ€μΉ λ° μ€ν
```
$ git clone https://lab.ssafy.com/s06-final/S06P31D207        #νλ‘μ νΈ λ°κΈ°(Git)
$ npm install #Front λΉλ
$ npm run build #Front page λΉλ
$ gradlew clean build #jar νμΌ μμ±
```
### 7. NGINX μ€μ 

-
  ### 7-1. NGINX μΈμ¦μ λ±λ‘ 
  ```
  $ sudo certbot certonly--standalone                                   
  [sudo] password for dev:   <λΉλ°λ²νΈ νΉμ κ·Έλ₯ μν°ν€>
  Saving debug log to /var/log/letsencrypt/letsencrypt.log
  Plugins selected: Authenticator standalone, Installer None
  Enter email address (used for urgent renewal and security notices)
  (Enter 'c' to cancel): λ³ΈμΈ λ°μ μ΄λ©μΌ

  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  Please read the Terms of Service at
  https://letsencrypt.org/documents/LE-SA-v1.2-November-15-2017.pdf. You must
  agree in order to register with the ACME server. Do you agree?
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  (Y)es/(N)o: Y <- ACME μ½κ΄μ λμνλμ§ Nμ νμ μ§νλΆκ°

  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  Would you be willing, once your first certificate is successfully issued, to
  share your email address with the Electronic Frontier Foundation, a founding
  partner of the Let's Encrypt project and the non-profit organization that
  develops Certbot? We'd like to send you email about our work encrypting the web,
  EFF news, campaigns, and ways to support digital freedom.
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  (Y)es/(N)o: N <- μ΄λ©μΌμ ν΅ν΄ Let's Encrypt νλ‘μ νΈ μ λ³΄λ₯Ό λ°μλ³Όμ§
  Please enter in your domain name(s) (comma and/or space separated)
  (Enter 'c' to cancel): k6d207.p.ssafy.io  <- {1} μΈμ¦μλ₯Ό λ°κΈν  λλ©μΈ μλ ₯


  IMPORTANT NOTES:
  - Congratulations! Your certificate and chain have been saved at:
    /etc/letsencrypt/live/k6d207.p.ssafy.io/fullchain.pem <- {2} λ°κΈλ μΈμ¦μ κ²½λ‘
    Your key file has been saved at:
    /etc/letsencrypt/live/k6d207.p.ssafy.io/privkey.pem <- {2} λ°κΈλ μΈμ¦μ κ²½λ‘
    ... κ·Έ μΈ λλ¨Έμ§ μ€λͺ
  ```
  ### 7-2. NGINX default νμΌ μ€μ 
  ```
  $ cd /etc/nginx/sites-enables
  $ sudo vi default // μ€μ νμΌ μ΄κΈ°
  ```

  ```
  server{
          root /home/ubuntu/deploy/build;

          # Add index.php to the list if you are using PHP
          index index.html index.htm index.nginx-debian.html;

          server_name k6d207.p.ssafy.io;

          location / {
                  # First attempt to serve request as file, then
                  # as directory, then fall back to displaying a 404.
                  try_files $uri $uri/ /index.html;
                  #proxy_pass http://localhost:3000;
                  #return 301 https://$host$request_uri;

          }

          location /api{
                  proxy_pass http://localhost:8080;
                  proxy_http_version 1.1;
                  proxy_set_header Connection "";

                  include /etc/nginx/proxy_params;
          }
      listen [::]:443 ssl ipv6only=on; # managed by Certbot
      listen 443 ssl; # managed by Certbot
      ssl_certificate /etc/letsencrypt/live/k6d207.p.ssafy.io/fullchain.pem; # managed by Certbot
      ssl_certificate_key /etc/letsencrypt/live/k6d207.p.ssafy.io/privkey.pem; # managed by Certbot
      include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
      ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
  }
  server {
      if ($host = k6d207.p.ssafy.io) {
          return 301 https://$host$request_uri;
      } # managed by Certbot


          listen 80 ;
          listen [::]:80 ;
      server_name k6d207.p.ssafy.io;
      return 404; # managed by Certbot


  }

  ```

### 8. Crawling μ€μ 

```
$ cd home/ubuntu/crawling/
$ python3 manage.py runserver 0:8000
```

* μλ ν¬λ‘€λ§μ μν Django μλ² κ³μ μ΄κΈ°

```
# μ€ν¬λ¦° μ€μΉ 
$ sudo apt-get install screen
$ screen -S build1
$ screen -list
$ screen -r [screen λͺ]
$ cd home/ubuntu/crawling/
$ python3 manage.py runserver 0:8000
screen + a + d(μ€ν¬λ¦°μμ λμ€κΈ°)
```

### 9. μΈλΆμλΉμ€ μ΄μ©

- μΉ΄μΉ΄μ€ μμ Oauth 2.0
- μΉ΄μΉ΄μ€ Map API
- Firebase Storage 



