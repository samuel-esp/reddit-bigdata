install.packages("data.table")
install.packages("ggplot2")
install.packages("dplyr")
library(ggplot2)
library(data.table)
library(dplyr)

data <- read.csv("/Users/samuel/Desktop/reddit-bigdata-bis/r-datasets/negative-reddit.tsv", sep = "\t")
data

data[is.na(data)] <- 0

#subreddit con piu' approvazione ai post negativi
subredditByScore = aggregate(data$score, by=list(Category=data$subreddit), FUN=mean)
print(test)
ggplot(subredditByScore, aes(x=x, y=Category)) + geom_bar(stat='identity', width=1)

#subreddit con piu' commenti sui post negativi 
subredditByComments = aggregate(data$commentsCount, by=list(Category=data$subreddit), FUN=mean)
print(test)
ggplot(subredditByComments, aes(x=x, y=Category)) + geom_bar(stat='identity', width=1)

#post con piu'score  per ogni subreddit
require(data.table) 
group <- as.data.table(data)
maxScorePost <- group[group[, .I[which.max(score)], by=subreddit]$V1]

#post con meno score  per ogni subreddit
require(data.table) 
group <- as.data.table(data)
minScorePost <- group[group[, .I[which.min(score)], by=subreddit]$V1]

#subreddit piu' presente nel dataset -> maggiore polarita' nella negativita'
subredditCount = count(data, data$subreddit)
ggplot(subredditCount, aes(x=data$subreddit, y=n)) + geom_bar(stat='identity', width=1)

#utenti con atteggiamento piu' negativo tra tutti i subreddit esaminati
mostNegativeUser = count(data, data$user)



