<a name="readme-top"></a>
<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">Blog Portfolio</h3>

  <p align="center">
    One page blog as portfolio with projects.
    <br />
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

![Main page Screen Shot][home]

Blog is deisgned for best user experience. Spring-boot and react are working together to make everything smooth.
Secured system has two roles - for admin and user. Each role has own privileges like admin can add, edit, delete posts or tags. On the other hand
user can register and comment posts. Page is responsive so browsing is possible on mobiles as well.

Features:
* JWT mechanism. Token is stored in local storage and refreshed if is expired.
* Backend has two profiles - for admin and user. Admin can manage whole application and user can comment posts.
* React framework make its easier to manage posts thanks to modals. When new post or tag is added only component is refreshed instead of whole page.
* Validation extension informs user if inputs are incorrect or there is problem with requests.
* Animations makes blog more interesting.

### Built With

Application is build on 2.7 spring-boot version and java 11, server side is communicating with mysql 8.0. Frontend is managed by React and photos are stored in s3 bucket. 
Server is running in heroku cloud, almost whole backend is tested with JUnit and Mockito.

[![Spring-Boot][Spring-Boot]][Spring-url]
[![MySQL][MySQL]][MySQL-url]
[![AWS][AWS]][AWS-url]
[![React][React]][React-url]
![HTML][HTML]
![CSS][CSS]
![JavaScript][JavaScript]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

Application is using external tools like S3 Bucket from Amazon.

### Prerequisites

Requirements for application to work properly: 
* S3 Bucket from Amazon

### Installation

_Here I will explain how to get all together so application can work on your machine. Whole process is quite long so I will provide links._

1. Create AWS account, there is available free tier for one year [AWS free tier]
    - then create [AWS S3 bucket]
    - the most important is to get [access and secret key] to your account. Paste the correct username and password into the application.properties file.
3. Build backen JAR file required for Dockerfile
    ```sh
    mvnw clean package -DskipTests
    ```
4. Run docker compose command to build project from Dockerfile's:
    ```sh
    docker compose up -d
    ```
5. Open browser and navigate to http://localhost:9090

6. There is no default or admin user to be able to add or edit post you need to manualy add admin role to registered user
    - get into docker container
      - docker exec -it blog-db bash
    - login into mysql as root user
      - mysql -uroot -p
    - use blog database
      - use blog-db;
    - insert into user_roles table new added user id and role id assigned to admin
      - insert into (user_id, role_id) values ('1', '2');

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage
1. **Login and register modals. During registration user needs to choose gender but he can also write his own. This choice generates avatar
     for profile, if user will choose gender "other" plugin will generate random avatar.**
  ![Login and Register][log-reg]
  
2. **About section has animations triggered on scroll into by user. CV button border has infinite loop animation.**
  ![About][about]
  
3. **Projects section has also animation when user scroll in or out. If logged in user has admin privileges on screen apears few more buttons.
     Admin can add, edit or delete posts, same with tags under posts.**
  ![Projects][projects]
  
4. **Comments component has previous generated user avatar, nickname and comment date creation. When user is logged in 
     on sreen are available buttons to manage posted comment. Validations make impossible to ingerent into someone else comment.**
  ![Comments][comments]
  
5. **Footer has all needed links and when user scroll further then about section on screen appears arrow to scroll back to top.**
  ![Footer][footer]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Email - mateusz.uranowski@onet.pl

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Resources I've used to create this project!

* [Image upload](https://github.com/amigoscode/spring-s3-react-file-upload)
* [React interceptors, axios api](https://www.bezkoder.com/)
* [Random Avatar API](https://bigheads.io/)
* [Validation Popup](https://fkhadra.github.io/react-toastify/introduction)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[home]: readme-img/home.png
[Spring-Boot]: https://img.shields.io/badge/Spring--Boot-black?logo=springboot&logoColor=6DB33F
[Spring-url]: https://spring.io/
[MySQL]: https://img.shields.io/badge/MySQL-3e4149?logo=mysql&logoColor=%234479A1
[MySQL-url]: https://www.mysql.com/
[AWS]: https://img.shields.io/badge/AWS-fe9900?logo=amazonaws
[AWS-url]: https://aws.amazon.com/
[React]: https://img.shields.io/badge/React-black?logo=react
[React-url]: https://reactjs.org/
[HTML]: https://img.shields.io/badge/HTML-white?logo=html5
[CSS]: https://img.shields.io/badge/CSS-264ee4?logo=css3
[JavaScript]: https://img.shields.io/badge/JavaScript-black?logo=javascript

[AWS free tier]: https://aws.amazon.com/free/?all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all
[AWS S3 bucket]: https://docs.aws.amazon.com/AmazonS3/latest/userguide/creating-bucket.html
[GMAIL]: https://support.google.com/mail/answer/56256?hl=en
[access and secret key]: https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html

[log-reg]: readme-img/log-reg.jpg
[about]: readme-img/about.png
[projects]: readme-img/projects.jpg
[vehicle]: readme-img/vehicle.png
[comments]: readme-img/comments.jpg
[footer]: readme-img/footer.png
