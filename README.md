## Custom Slack Bot

#### Purpose  
Current purpose of this bot is to send a stand up reminder at 08:05:30 
with 3 random names to determine who will run the morning stand up.

#### How to interact with the bot

1. `@botname choose`  
Returns one random name from a file  
1. `@botname choose 2` | `@botname choose 3`    
Returns two/three random names from a file  
1. `@botname help`  
Returns a guide on how to use the bot
1. `@botname hi` | `@botname hey` | `@botname hello`  
Returns a greeting  
1. `@botname :taco:`
Returns a random emoji

#### To Run Locally:  

1. Create a slack workspace or login to an existing one 
1. [Create a bot user](https://xpbot.slack.com/apps/new/A0F7YS25R-bots) for the workspace 
1. In the bot setting, look for **API Token** and replace `${SLACK_TOKEN}` in `application.yml` with the token
1. In the slack workspace, click general channel and get the **General Channel ID** by looking at the end of the URL and replace `${SLACK_GENERAL_CHANNEL}` in `application.yml` with the channel id 
    - Example of a channel id: CXXQ11CX0
1. Look for an icon/picture for your bot, then replace `${SLACK_ICON_URL}` in `application.yml` with the image link
1. Replace `${SLACK_NAME}` with a name of your bot
1. After replacing all the variables, run `./gradlew clean build bootRun`
1. Once the application is running, invite the bot to your workspace
1. Interact with the bot