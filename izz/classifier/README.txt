MOOD: Suspence 0, Action 1, Dramatic 2, Happy 3, Sad 4, Nuetral 5.
WORD: Adjectives, Adverbs, and verbs.
Trainer
  usage: java Trainer modelFile fileToTag
  example: java Trainer ../extra/models/english-bidirectional-distsim.tagger sample-input.txt
  Takes a file and looping through every sentence. The user chooses the MOOD of the sentence.
  For every WORD in the sentence, the values in WordVals are updated depending on the user input.

Tester
  usage: Tester file
  example: java Tester randTest.txt
  Reads the word bank. For every sentence in the given file, look at every word, if its in wordVals, update MOOD values. 
  Output the result for every sentence and all the worlds that are recognised.

WordVals
  Contains WORDs and the frequency of occurance for every type of MOOD.
  Some Nouns and other words were added for sake of accuracy, examples:
    1. basilisk
    2. voldamort

Initial idea is we create a cluster of words for every mood.
Use Word Net to find synonyms of input words.
calculate proximity to every cluster by matching synonyms with clusters.
Assign word to cluster of higher proximity

Download and check out WordNet: http://wordnet.princeton.edu/wordnet/download/current-version/
After making the file and adding it to your PATH, use commands like:
  1. Synonyms for nouns, verbs, adjectives and adverbs: wn word syns{n|v|a|r}
  2. Antonyms: wn dead -antsn
  3. Familiarity is a measure of how common a word is. We could use it to create weights for words:
    wn word faml{n|v|a|r}

Useful links to expand program in later iterations (Stanford nlp Classifier, Sentiment Analysis):
  1. http://nlp.stanford.edu/software/classifier.shtml
  2. http://www-nlp.stanford.edu/wiki/Software/Classifier
  3. http://www-nlp.stanford.edu/wiki/Software/Classifier/Sentiment
  4. http://wordnet.princeton.edu