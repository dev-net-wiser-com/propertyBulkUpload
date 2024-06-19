/*     */ package com.hmms.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnicodeReader
/*     */   extends Reader
/*     */ {
/*     */   PushbackInputStream internalIn;
/*  31 */   InputStreamReader internalIn2 = null;
/*     */ 
/*     */ 
/*     */   
/*     */   String defaultEnc;
/*     */ 
/*     */   
/*     */   private static final int BOM_SIZE = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicodeReader(InputStream in, String defaultEnc) {
/*  43 */     this.internalIn = new PushbackInputStream(in, 4);
/*  44 */     this.defaultEnc = defaultEnc;
/*     */   }
/*     */   
/*     */   public String getDefaultEncoding() {
/*  48 */     return this.defaultEnc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/*  56 */     if (this.internalIn2 == null) return null; 
/*  57 */     return this.internalIn2.getEncoding();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() throws IOException {
/*     */     String encoding;
/*     */     int unread;
/*  65 */     if (this.internalIn2 != null) {
/*     */       return;
/*     */     }
/*  68 */     byte[] bom = new byte[4];
/*     */     
/*  70 */     int n = this.internalIn.read(bom, 0, bom.length);
/*     */     
/*  72 */     if (bom[0] == 0 && bom[1] == 0 && 
/*  73 */       bom[2] == -2 && bom[3] == -1) {
/*  74 */       encoding = "UTF-32BE";
/*  75 */       unread = n - 4;
/*  76 */     } else if (bom[0] == -1 && bom[1] == -2 && 
/*  77 */       bom[2] == 0 && bom[3] == 0) {
/*  78 */       encoding = "UTF-32LE";
/*  79 */       unread = n - 4;
/*  80 */     } else if (bom[0] == -17 && bom[1] == -69 && 
/*  81 */       bom[2] == -65) {
/*  82 */       encoding = "UTF-8";
/*  83 */       unread = n - 3;
/*  84 */     } else if (bom[0] == -2 && bom[1] == -1) {
/*  85 */       encoding = "UTF-16BE";
/*  86 */       unread = n - 2;
/*  87 */     } else if (bom[0] == -1 && bom[1] == -2) {
/*  88 */       encoding = "UTF-16LE";
/*  89 */       unread = n - 2;
/*     */     } else {
/*     */       
/*  92 */       encoding = this.defaultEnc;
/*  93 */       unread = n;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     if (unread > 0) this.internalIn.unread(bom, n - unread, unread);
/*     */ 
/*     */     
/* 100 */     if (encoding == null) {
/* 101 */       this.internalIn2 = new InputStreamReader(this.internalIn);
/*     */     } else {
/* 103 */       this.internalIn2 = new InputStreamReader(this.internalIn, encoding);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 109 */     init();
/* 110 */     this.internalIn2.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(char[] cbuf, int off, int len) throws IOException {
/* 115 */     init();
/* 116 */     return this.internalIn2.read(cbuf, off, len);
/*     */   }
/*     */ }


/* Location:              C:\Users\ennio\Downloads\HMMS_netwiser_coa_WS\WEB-INF\classes\!\com\hkhs\hmms\co\\util\UnicodeReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */