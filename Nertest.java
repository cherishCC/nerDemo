package com.srcb;

import java.io.IOException;
import java.util.List;


import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ie.crf.CRFCliqueTree;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;

public class Nertest {

    public static void main(String[] args) throws IOException {

//      指定模型path
        String serializedClassifier = "D:\\PycharmPro\\ELD\\stanford-ner-2017-06-09\\classifiers\\english.all.3class.distsim.crf.ser.gz";
//      生成识别器
        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);

//      识别试例1
        String s1 = "The next day is Friday";
        System.out.println(classifier.classifyToString(s1));
//      识别试例2,识别成功,在Rami后面加上了PERSON标签,大学后加上了ORGANIZATION标签等
        String s2 = "Rami Eid is studying at Stony Brook University in NY.";
        System.out.println(classifier.classifyToString(s2));

//      官方给的一个输出全标签的例子,因为给的输出不够,我就是想在这个后面多加一项概率的输出
        String[] example = {"Good afternoon Rajat Raina, how are you today?",
                "I go to school at Stanford University, which is located in California." };
        // This prints out all the details of what is stored for each token
        int i=0;
        for (String str : example) {
            for (List<CoreLabel> lcl : classifier.classify(str)) {
                for (CoreLabel cl : lcl) {
                    System.out.print(i++ + ": ");
                    System.out.println(cl.toShorterString());
                }
            }
        }
/**
 * 目标: 主要就是下面搞不定,我想要输出每一个实体的置信度,也就是那个单词后面跟着的标签的概率
 *
 * 官方给的说明
 *Probabilities assigned by the CRF can be interrogated using either the  printProbsDocument() or getCliqueTrees() methods.
 * 就是说可以用printProbsDocument() or getCliqueTrees() 方法来输出.
 *
 *  网上给的例子是这样https://stackoverflow.com/questions/26612999/display-stanford-ner-confidence-score
 * 可能因为网上的版本不一样,现在classifer这个实例,已经没有getCliqueTree这个方法了
 *
 * 我自己看的源码edu.stanford.nlp.ie.crf.CRFClassifier下面有getCliqueTree(),是个读文件的方法
 * 并且在edu.stanford.nlp.ie.crf.CRFClassifier 的 printFirstOrderProbsDocument中有使用
 * 我就是想让它最后输出时像printFirstOrderProbsDocument中一样,把laber和probability都输出来
 *
 *  printFirstOrderProbsDocument
 * */
        String sentences = "Rami Eid is studying at Stony Brook University in NY.";
        List<List<CoreLabel>> classifiedLabels = classifier.classify(sentences);


//        CRFCliqueTree<String> cliqueTree = classifier.getCliqueTree(classifiedLabels);

    }
}

