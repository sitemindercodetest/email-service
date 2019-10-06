export AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID>
export AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY>
export AWS_DEFAULT_REGION=ap-southeast-2

aws sqs create_queue --queue-name st-email-service.fifo --attributes FifoQueue=true