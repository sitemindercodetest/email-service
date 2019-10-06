export AWS_ACCESS_KEY_ID=<AWS-KEY>
export AWS_SECRET_ACCESS_KEY=<AWS-SECRET>
export AWS_DEFAULT_REGION=ap-southeast-2

aws sqs create_queue --queue-name st-email-service.fifo --attributes FifoQueue=true