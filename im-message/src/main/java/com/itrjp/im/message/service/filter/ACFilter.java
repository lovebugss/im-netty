package com.itrjp.im.message.service.filter;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;


/**
 * 简单的AC 过滤器(chatGPT生成)
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 14:48
 */
public class ACFilter {
    private final ACNode root;

    public ACFilter() {
        this.root = new ACNode('\0');
    }

    // 将黑词加入 AC 自动机中
    public void addWord(String word) {
        ACNode cur = root;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            String[] pinyin = null;
            try {
                // 判断是否为中文字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (pinyin != null) {
                for (String py : pinyin) {
                    cur = addChar(cur, py.charAt(0));
                }
            } else {
                cur = addChar(cur, c);
            }
        }
        cur.isWord = true;
    }

    private ACNode addChar(ACNode cur, char c) {
        if (cur.children[c - 'a'] == null) {
            cur.children[c - 'a'] = new ACNode(c);
        }
        return cur.children[c - 'a'];
    }

    // 建立 AC 自动机的 fail 指针
    public void build() {
        List<ACNode> queue = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                root.children[i].fail = root;
                queue.add(root.children[i]);
            } else {
                root.children[i] = root;
            }
        }
        while (!queue.isEmpty()) {
            ACNode cur = queue.remove(0);
            for (int i = 0; i < 26; i++) {
                if (cur.children[i] != null) {
                    ACNode p = cur.fail;
                    while (p.children[i] == null) {
                        p = p.fail;
                    }
                    cur.children[i].fail = p.children[i];
                    queue.add(cur.children[i]);
                }
            }
        }
    }

    // 检查文本中是否包含黑词
    public boolean contains(String text) {
        ACNode p = root;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String[] pinyin = null;
            try {
                // 判断是否为中文字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (pinyin != null) {
                for (String py : pinyin) {
                    while (p.children[py.charAt(0) - 'a'] == null) {
                        p = p.fail;
                    }
                    p = p.children[py.charAt(0) - 'a'];
                    if (p.isWord) {
                        return true;
                    }
                }
            } else {
                while (p.children[c - 'a'] == null) {
                    p = p.fail;
                }
                p = p.children[c - 'a'];
                if (p.isWord) {
                    return true;
                }
            }
        }
        return false;
    }


    private static class ACNode {
        char c;
        ACNode[] children;
        ACNode fail;
        boolean isWord;

        public ACNode(char c) {
            this.c = c;
            this.children = new ACNode[26];
            this.isWord = false;
        }
    }
}
