install.packages("data.table")
install.packages("ggplot2")
install.packages("plotly")
install.packages("dplyr")
library(ggplot2)
library(plotly)
library(data.table)
library(dplyr)

data <- read.csv("/Users/samuel/Desktop/reddit-bigdata-bis/r-datasets/positive-reddit.tsv", sep = "\t")
data

data[is.na(data)] <- 0

#subreddit con piu' approvazione ai post positivi
subredditByScore = aggregate(data$score, by=list(Category=data$subreddit), FUN=mean)
ggplot(subredditByScore, aes(x=x, y=Category)) + geom_bar(stat='identity', width=1)

#subreddit con piu' commenti sui post positivi 
subredditByComments = aggregate(data$commentsCount, by=list(Category=data$subreddit), FUN=mean)
ggplot(subredditByComments, aes(x=x, y=Category)) + geom_bar(stat='identity', width=1)

#post con piu'score  per ogni subreddit
require(data.table) 
group <- as.data.table(data)
maxScorePost <- group[group[, .I[which.max(score)], by=subreddit]$V1]

#post con meno score  per ogni subreddit
require(data.table) 
group <- as.data.table(data)
minScorePost <- group[group[, .I[which.min(score)], by=subreddit]$V1]

#subreddit piu' presente nel dataset -> maggiore polarita' nella positivita'
subredditCount = count(data, subreddit = data$subreddit)
ggplot(subredditCount, aes(x=n, y=subreddit)) + geom_bar(stat='identity', width=1)

#utenti con atteggiamento piu' negativo tra tutti i subreddit esaminati
mostPositiveUser = count(data, data$user)

scoreByDate = aggregate(data$score, by=list(Category=data$time), FUN=mean)
p <- ggplot(scoreByDate, aes(x=x, y=Category)) + geom_line() + xlab("")
