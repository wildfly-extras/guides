# Sample Jakarta REST Service invoking an external LLM

We need an LLM for this example; we propose a couple of options here but feel free to use any other LLM supported by [LangChain4J](https://docs.langchain4j.dev/integrations/language-models):

* Option 1: Ollama
* Option 2: Mistral

NOTE: We are using the Ollama option in this project

## LLM: Ollama

Here, we are running our LLM locally inside a container image; 

As described in [Ollama Docker Image](https://ollama.com/blog/ollama-is-now-available-as-an-official-docker-image), `Ollama` 
can be used as a Docker image;

Start the `Ollama` container:

```shell
podman volume create ollama
podman run --rm -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

And then install the `llama3` LLM inside it:

```shell
podman exec -it ollama ollama pull llama3
```

Check `` is running:

```shell
curl http://localhost:11434/api/generate -d '{
  "model": "llama3",
  "prompt":" Why is the colour of sea blue ?"
}'
```

```shell
export OLLAMA_CHAT_URL=http://127.0.0.1:11434
export OLLAMA_CHAT_LOG_REQUEST=true
export OLLAMA_CHAT_LOG_RESPONSE=true
export OLLAMA_CHAT_TEMPERATURE=0.9
export OLLAMA_CHAT_MODEL_NAME=llama3
```

## LLM: Mistral

Here, we are not installing anything locally and we are just using the remote Mistral APIs that can be used for free;

NOTE: remember to use the `mistral-ai-chat-model` layer in you `pom.xml` file (replace `ollama-chat-model` with `mistral-ai-chat-model`)

# Mistral AI free key

Go to https://console.mistral.ai/home and create a free API Key (no credit card required);

Set Mistral Key:  
```shell
export MISTRAL_API_KEY=...
```

## Build and Test your application

```shell
mvn clean install -DskipTests
```

```shell
./target/server/bin/standalone.sh
```

Point your browser at http://localhost:8080/api/tom and, in case you are using `llama3`, you will see something like:

```json lines
AiMessage { text = "Nice to meet you, Tom! I'm happy to chat with you. What's on your mind today?" toolExecutionRequests = [] }
```

If, after this, you point your browser to http://localhost:8080/api/get-previous-name, you will see something like:

```json lines
AiMessage { text = "I already knew that, Tom! You told me earlier, remember? Your name is... (drumroll) ...Tom!" toolExecutionRequests = [] }
```

which proves that the chat memory actually works and the LLM is able to tell your name from the previous conversation;

## Build

```shell
mvn clean package
```

## Container Image

```shell
podman build -t my-jaxrs-app-llm:latest .
```
