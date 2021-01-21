# Ktor Template walkthrough

[![Build Status][github-actions-svg]][github-actions]

This walkthrough will explain you how to correctly create a microservice from a Ktor Template using our DevOps Console.

## Create a microservice

In order to do so, access to [Mia-Platform DevOps Console](https://console.cloud.mia-platform.eu/login), create a new project and go to the **Design** area.  
From the Design area of your project select _Microservices_ and then create a new one, you have now reached [Mia-Platform Marketplace](https://docs.mia-platform.eu/development_suite/api-console/api-design/marketplace/)!  
In the marketplace you will see a set of Examples and Templates that can be used to set-up microservices with a predefined and tested function.  

For this walkthrough select the following example: **Ktor Template**.
Give your microservice the name you prefer, in this walkthrough we'll refer to it with the following name: **my-ktor-service-name**. Then, fill the other required fields and confirm that you want to create a microservice.  
A more detailed description on how to create a Microservice can be found in [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#2-service-creation) section of Mia-Platform documentation.

## Expose an endpoint to your microservice

In order to access to your new microservice it is necessary to create an endpoint that targets it.  
In particular, in this walkthrough you will create an endpoint to your microservice *my-ktor-service-name*. To do so, from the Design area of your project select _Endpoints_ and then create a new endpoint.
Now you need to choose a path for your endpoint and to connect this endpoint to your microservice. Give to your endpoint the following path: **/ktor-template**. Then, specify that you want to connect your endpoint to a microservice and, finally, select *my-ktor-service-name*.  
Step 3 of [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#3-creating-the-endpoint) section of Mia-Platform documentation will explain in detail how to create an endpoint from the DevOps Console.

## Save your changes

After having created an endpoint to your microservice you should save the changes that you have done to your project in the DevOps console.  
Remember to choose a meaningful title for your commit (e.g "created service my_ktor_service_name"). After some seconds you will be prompted with a popup message which confirms that you have successfully saved all your changes.  
Step 4 of [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#4-save-the-project) section of Mia-Platform documentation will explain how to correctly save the changes you have made on your project in the DevOps console.

## Deploy

Once all the changes that you have made are saved, you should deploy your project through the DevOps Console. Go to the **Deploy** area of the DevOps Console.  
Once here select the environment and the branch you have worked on and confirm your choices clicking on the *deploy* button. When the deploy process is finished you will receveive a pop-up message that will inform you.  
Step 5 of [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#5-deploy-the-project-through-the-api-console) section of Mia-Platform documentation will explain in detail how to correctly deploy your project.

## Try it

Now, if you copy/paste the following url in the search bar of your broser (remember to replace `<YOUR_PROJECT_HOST>` with the real host of your project):

```shell
https://<YOUR_PROJECT_HOST>/ktor-template/
```

you should see a *Whitelabel Error Page*. This behaviour is expected since this template has no routes defined.

Wonderful! You are now ready to start customizing your service! Read next section to learn how.

## Look inside your repository

Go back to _Microservices_, select *my-ktor-service-name* and access its git repository from the DevOps Console.

This is a kotlin project, in this repository you will find a [directory](https://github.com/mia-platform-marketplace/Ktor-Template/tree/master/src/main/kotlin/eu/miaplatform/service) 
where you can find most of the source code of the template that you have created.

We have already created the controller package to group all the files with route definitions, you can change the file 
structure as you prefer.

## Add a Welcome route

Add inside this folder [directory](https://github.com/mia-platform-marketplace/Ktor-Template/tree/master/src/main/kotlin/eu/miaplatform/service/controller) a file named **HelloWorld.kt** with the following content:

```kotlin
package eu.miaplatform.service.controller
import io.ktor.application.Application
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tag
import com.fasterxml.jackson.annotation.JsonProperty
import eu.miaplatform.service.model.ServiceTag

data class HelloWorldResponse (
    @JsonProperty("message")
    @get:JsonProperty("message")
    val message: String
)

fun helloWorld(application: Application) {

    application.apiRouting {
        route("/") {
            tag(ServiceTag) {
                get<Unit, HelloWorldResponse>(
                    info("The description of the endpoint")
                ) { params ->
                    var response = HelloWorldResponse("Hello world!")
                    respond(response)
                }
            }
        }
    }
}
```

In line `respond(HelloWorldResponse("Hello world!"))` you are creating an instance of a class called HelloWorld. 

As you can notice we are not using the original ktor `routing` but the `apiRouting` that permits you to construct 
automatically the `open-api.json`.`

For more details about how to use the `apiRouting see https://github.com/papsign/Ktor-OpenAPI-Generator

You have to import this file in the [file](https://github.com/mia-platform-marketplace/Ktor-Template/tree/master/src/main/kotlin/eu/miaplatform/service/ServiceApplication.kt) inside the `apiRouting` installation. 

```kotlin
import eu.miaplatform.service.controller.helloWorld

...
    ...
    apiRouting {
        //here goes your controller
        helloWorld(this@module)
    }
    ...

```

In this file you can see the instantiation of the library that creates the swaggers reachable at the endpoint `/documentation`.

After committing these changes to your repository, you can go back to Mia Platform DevOps Console.  
Go to the **Deploy** area of the DevOps Console and deploy your project in a similar way to what you have done before modifying your git repository.

## Try it again

Once the deploy process is finished, go back to

```shell
https://<YOUR_PROJECT_HOST>/ktor-template/
```

or type the following in your terminal:

```shell
curl https://<YOUR_PROJECT_HOST>/ktor-template/
```

What you should see now is a very simple welcome message:

```json
{"message":"Hello World!"}
```

Congratulations! You have successfully learnt how to use our Ktor Template on the DevOps Console!

[github-actions]: https://github.com/mia-platform-marketplace/Ktor-Template/actions
[github-actions-svg]: https://github.com/mia-platform-marketplace/Ktor-Template/workflows/Java%20CI%20with%Gradle/badge.svg