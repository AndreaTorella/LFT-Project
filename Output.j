.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 invokestatic Output/read()I
 istore 0
 invokestatic Output/read()I
 istore 1
 invokestatic Output/read()I
 istore 2
 invokestatic Output/read()I
 istore 3
 iload -1
 iload 1
 if_icmpgt L1
 goto L2
L1:
 iload -1
 iload 2
 if_icmpgt L4
 goto L5
L4:
 iload -1
 invokestatic Output/print(I)V
 ldc 2
 ldc 2
 idiv 
 ldc 2
 ldc 54
 isub 
 ldc 2
 ldc 4
 isub 
 iadd 
 ldc 2
 iadd 
 ldc 9
 ldc 3
 isub 
 ldc 2
 iload -1
 ldc 2
 ldc 3
 idiv 
 iadd 
 iload 1
 ldc 3
 imul 
 ldc 4
 imul 
 iadd 
 imul 
 imul 
 iadd 
 imul 
 invokestatic Output/print(I)V
 goto L3
L5:
 iload 2
 iload 3
 if_icmpgt L7
 goto L8
L7:
 ldc 2
 ldc 2
 idiv 
 ldc 3
 ldc 3
 idiv 
 ldc 3
 ldc 3
 idiv 
 imul 
 imul 
 invokestatic Output/print(I)V
 ldc 2
 ldc 2
 idiv 
 ldc 2
 ldc 54
 isub 
 ldc 2
 ldc 4
 isub 
 iadd 
 ldc 2
 iadd 
 ldc 9
 ldc 3
 isub 
 ldc 2
 iload -1
 ldc 2
 ldc 3
 idiv 
 iadd 
 iload 1
 ldc 3
 imul 
 ldc 4
 imul 
 iadd 
 imul 
 imul 
 iadd 
 imul 
 invokestatic Output/print(I)V
 goto L6
L8:
 iload 2
 invokestatic Output/print(I)V
L6:
L3:
 goto L0
L2:
 iload 1
 iload 2
 if_icmpgt L10
 goto L11
L10:
 ldc 2
 invokestatic Output/print(I)V
 goto L9
L11:
 iload 3
 invokestatic Output/print(I)V
L9:
L0:
L14:
 iload -1
 ldc 0
 if_icmpgt L12
 goto L13
L12:
 ldc 2
 ldc 2
 idiv 
 ldc 2
 ldc 54
 isub 
 ldc 2
 ldc 4
 isub 
 iadd 
 ldc 2
 iadd 
 ldc 9
 ldc 3
 isub 
 ldc 2
 iload 4
 ldc 2
 ldc 3
 idiv 
 iadd 
 iload 1
 ldc 3
 imul 
 ldc 4
 imul 
 iadd 
 imul 
 imul 
 iadd 
 imul 
 istore 4
 iload 4
 ldc 2
 ldc 2
 idiv 
 ldc 2
 ldc 54
 isub 
 ldc 2
 ldc 4
 isub 
 iadd 
 ldc 2
 iadd 
 ldc 9
 ldc 3
 isub 
 ldc 2
 iload 4
 ldc 2
 ldc 3
 idiv 
 iadd 
 iload 1
 ldc 3
 imul 
 ldc 4
 imul 
 iadd 
 imul 
 imul 
 iadd 
 iadd 
 isub 
 invokestatic Output/print(I)V
 ldc 6
 invokestatic Output/print(I)V
 goto L14
L13:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

