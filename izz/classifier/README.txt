MOOD: Suspence 0, Action 1, Dramatic 2, Happy 3, Sad 4, Nuetral 5.

Trainer:
  usage: java Trainer modelFile fileToTag
  Takes a file and looping through every sentence. The user chooses the MOOD of the sentence. For every word in the sentence, the values in WordVals are updated depending on the user input.

Tester:
  usage: Tester file
  Reads the word bank. For every sentence in the given file, look at every word, if its in wordVals, update MOOD values. Output the result for every sentence and all the worlds that are recognised.

Useful links to expand program (Stanford nlp Classifier, Sentiment Analysis):
  1. http://nlp.stanford.edu/software/classifier.shtml
  2. http://www-nlp.stanford.edu/wiki/Software/Classifier
  3. http://www-nlp.stanford.edu/wiki/Software/Classifier/Sentiment
  4. http://wordnet.princeton.edu