
__Table of Contents__

- [High level components](#high-level-components)

- [High level architectural considerations](#high-level-architectural-considerations)
    - [Avoid data loss - Reliability](#avoid-data-loss---reliability)
    - [Scalability](#scalability)
    - [Operational excellence](#operational-excellence)
    - [Security](#security)
    - [Cost effective](#cost-effective)

- [Limitations of the architecture](#limitations-of-the-architecture)

- [Other options considered](#other-options-considered)
    - [Email Service API - SQS - Lambda](#email-service-api---sqs---lambda)
    - [API Gateway - Lambda](#api-gateway---lambda)
    
- [Diagrams](#diagrams)
    - [Current architecture](#current-architecture)
    - [System overview](#system-overview)
    - [Email service components](#email-service-components)
    - [Email service worker components](#email-service-worker-components)
    
    
# High level components

- Email Service API
- SQS Email Queue
- Email Service Worker

# High level architectural considerations

## Avoid data loss - Reliability
In this architecture the data loss is avoided by storing the message in SQS queue as soon as the message is received. The durability of the messages are same as the durability of SQS, which persists the data in Multi AZ and highly durable (I am not able to find the exact 9's for durability in AWS doc). The other scenarios in which data loss might occur during network transfer like while communicating from the email-service-api to SQS the end user will get 500 as response which can be used to re-send the message to the API by the consumer. Also both email-service-api and email-service-worker should be deployed multi AZ which will ensure the high availability of the system.  
      
## Scalability
The API servers are behind the load-balancer and auto-scalable, which makes sure the traffic is spread across multiple instances of API server. The FIFO SQS can handle 300 write request per second, if we hit the limit then we can raise a limit increase request. We can also have more than one listener application consuming the messages from FIFO SQS queue which provides this can be auto scaled based on the cloud watch alarm of 'ApproximateAgeOfOldestMessage' in the queue.       

## Operational excellence
As the architecture is decoupled as consumer(API) and processor(worker) they can be scaled individually based on cloud watch metrics, this enables the systems to scale independently. Also as they are decoupled the deployed/changes to the systems can be happen without affecting one another. This enables monitoring the systems using different metrics, which gives more insight to the systems.     

## Security
As these messages might contain PII data, we need to make sure that these messages are highly secured. We should use the SSE (server side encryption) in the SQS queue while the data is at rest and SSL/TLS while the data is in transit (for this exercise they are not encrypted) to protect the messages. For tracking purpose the unique ID of the messages can be used.
We should also make sure that the PII data is not being logged in log messages, if the are logged we need to ensure that the logs are being secured and doesn't go outside the organization.  

## Cost effective
As the systems both API and worker can be configured as auto scalable, we can maintain the minimum required resource all the time. And can leverage spot instance for auto scaling. This enables the cost of the infrastructure is directly proportional to the amount traffic to the system.  

# Limitations of the architecture
- Limit of the SQS message size is 256KB, emails having more than 256KB we might need to put them in s3 using this (https://github.com/awslabs/amazon-sqs-java-extended-client-lib)
- FIFO queues default limit is 300 req/sec (Write, Read and Delete), need to raise limit request or consider other alternative approach like Queue pooling [architecture](/images/scaled-version.png). The reason for choosing FIFO queue instead of standard queue is to avoid duplication. In strandad queue there could be duplication of messages in rare scenarios.
- Not able to connect leverage serveless architecture using lambda's. (AWS doesn't support lambda's trigger for FIFO queues).

# Other options considered

## Email Service API - SQS - Lambda
- Email Service API to standard SQS and then configure lambda trigger from SQS which will act as worker
    - Pros
        - Highly scalable (Higher limit to SQS)
        - Lambdas are triggered and automatically and process the messages from the queue.
        - Cost effective       
    - Cons
        - Standard queue can produce duplicate messages, which might be an issue (Can discuss with business as this is a rare case scenario)
        - Log aggregation from lambdas            
         
## API Gateway - Lambda
- API gateway as (email-service) to SQS and then lambda (worker)
    - Pros
        - Serverless
        - Ease of scalability
    - Cons
        - Data loss
        - Feedback to consumers form the API


# Diagrams

## Current architecture 

![Current Architecure](/images/current-version.png)

## System overview

![System overview](/images/c2.png)

## Email service components

![Email Service](/images/email-service-api.png)

## Email service worker components

![Email Service Worker](/images/email-service-worker.png)
 
