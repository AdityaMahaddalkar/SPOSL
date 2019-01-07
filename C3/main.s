	.file	"main.c"
	.section	.rodata
	.align 8
.LC0:
	.string	"\n1.ps\n2.fork\n3.join\n4.exec\n5.wait\n>"
.LC1:
	.string	"%s"
.LC2:
	.string	"%d"
.LC3:
	.string	"ps -A"
.LC4:
	.string	"%d\n"
.LC5:
	.string	"./EXEC"
.LC6:
	.string	"wait"
.LC7:
	.string	"Invalid"
	.text
	.globl	main
	.type	main, @function
main:
.LFB2:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movq	$.LC0, -8(%rbp)
	movq	-8(%rbp), %rax
	movq	%rax, %rsi
	movl	$.LC1, %edi
	movl	$0, %eax
	call	printf
	leaq	-12(%rbp), %rax
	movq	%rax, %rsi
	movl	$.LC2, %edi
	movl	$0, %eax
	call	__isoc99_scanf
	movl	-12(%rbp), %eax
	cmpl	$5, %eax
	ja	.L2
	movl	%eax, %eax
	movq	.L4(,%rax,8), %rax
	jmp	*%rax
	.section	.rodata
	.align 8
	.align 4
.L4:
	.quad	.L2
	.quad	.L3
	.quad	.L5
	.quad	.L11
	.quad	.L7
	.quad	.L8
	.text
.L3:
	movl	$.LC3, %edi
	call	system
	jmp	.L9
.L5:
	call	fork
	call	getpid
	movl	%eax, %esi
	movl	$.LC4, %edi
	movl	$0, %eax
	call	printf
	jmp	.L9
.L7:
	movl	$0, %esi
	movl	$.LC5, %edi
	call	execv
	jmp	.L9
.L8:
	movl	$.LC6, %edi
	call	system
	jmp	.L9
.L2:
	movl	$.LC7, %edi
	movl	$0, %eax
	call	printf
	jmp	.L9
.L11:
	nop
.L9:
	movl	$0, %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	main, .-main
	.ident	"GCC: (GNU) 4.8.3 20140911 (Red Hat 4.8.3-7)"
	.section	.note.GNU-stack,"",@progbits
