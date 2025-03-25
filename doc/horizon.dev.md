### 项目名称：

horizon

### 起始端口号计算：
`14166`
1. **获取每个字符的 ASCII 值**：
   每个字符都有一个唯一的 ASCII 值。将 "horizon" 中每个字符的完整 ASCII 值提取出来。
    - h -> 104
    - o -> 111
    - r -> 114
    - i -> 105
    - z -> 122
    - o -> 111
    - n -> 110

2. **加权求和**：
    - 权重规则：第 \(i\) 个字符的权重为 \(2^{i-1}\)（即从 1 开始递增的幂次）。
    - 计算加权和：
      \[
      \text{加权和} = (104 \times 2^0) + (111 \times 2^1) + (114 \times 2^2) + (105 \times 2^3) + (122 \times 2^4) + (
      111 \times 2^5) + (110 \times 2^6)
      \]
        - \(104 \times 1 = 104\)
        - \(111 \times 2 = 222\)
        - \(114 \times 4 = 456\)
        - \(105 \times 8 = 840\)
        - \(122 \times 16 = 1952\)
        - \(111 \times 32 = 3552\)
        - \(110 \times 64 = 7040\)
        - 总和：\(104 + 222 + 456 + 840 + 1952 + 3552 + 7040 = 14166\)

### git 初始化
1. 初始化本地仓库 git init
2. 提交全部代码到暂存区 git add .
3. 将暂存区代码保存到本地仓库 git commit -m "初始化后端工程"
4. 关联本地仓库和远程仓库  git remote add origin https://github.com/zhc-dev/horizon.git
5. 将代码推送至远程仓库的master分支 git push -u origin "main" 报错
6. 查看分支 git branch （* master） 重命名分支 git branch -m main （* main）
7. 重新推送 git push -u origin "main"
