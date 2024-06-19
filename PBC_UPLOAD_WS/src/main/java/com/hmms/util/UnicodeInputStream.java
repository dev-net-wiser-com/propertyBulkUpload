/*     */ package com.hmms.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
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
/*     */ public class UnicodeInputStream
/*     */   extends InputStream
/*     */ {
/*     */   PushbackInputStream internalIn;
/*     */   boolean isInited = false;
/*     */   String defaultEnc;
/*     */   String encoding;
/*     */   private static final int BOM_SIZE = 4;
/*     */   
/*     */   public UnicodeInputStream(InputStream in, String defaultEnc) {
/*  47 */     this.internalIn = new PushbackInputStream(in, 4);
/*  48 */     this.defaultEnc = defaultEnc;
/*     */   }
/*     */   
/*     */   public String getDefaultEncoding() {
/*  52 */     return this.defaultEnc;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/*  56 */     if (!this.isInited) {
/*     */       try {
/*  58 */         init();
/*  59 */       } catch (IOException ex) {
/*  60 */         IllegalStateException ise = new IllegalStateException("Init method failed.");
/*  61 */         ise.initCause(ise);
/*  62 */         throw ise;
/*     */       } 
/*     */     }
/*  65 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() throws IOException {
/*     */     int unread;
/*  73 */     if (this.isInited)
/*     */       return; 
/*  75 */     byte[] bom = new byte[4];
/*     */     
/*  77 */     int n = this.internalIn.read(bom, 0, bom.length);
/*     */     
/*  79 */     if (bom[0] == 0 && bom[1] == 0 && 
/*  80 */       bom[2] == -2 && bom[3] == -1) {
/*  81 */       this.encoding = "UTF-32BE";
/*  82 */       unread = n - 4;
/*  83 */     } else if (bom[0] == -1 && bom[1] == -2 && 
/*  84 */       bom[2] == 0 && bom[3] == 0) {
/*  85 */       this.encoding = "UTF-32LE";
/*  86 */       unread = n - 4;
/*  87 */     } else if (bom[0] == -17 && bom[1] == -69 && 
/*  88 */       bom[2] == -65) {
/*  89 */       this.encoding = "UTF-8";
/*  90 */       unread = n - 3;
/*  91 */     } else if (bom[0] == -2 && bom[1] == -1) {
/*  92 */       this.encoding = "UTF-16BE";
/*  93 */       unread = n - 2;
/*  94 */     } else if (bom[0] == -1 && bom[1] == -2) {
/*  95 */       this.encoding = "UTF-16LE";
/*  96 */       unread = n - 2;
/*     */     } else {
/*     */       
/*  99 */       this.encoding = this.defaultEnc;
/* 100 */       unread = n;
/*     */     } 
/*     */ 
/*     */     
/* 104 */     if (unread > 0) this.internalIn.unread(bom, n - unread, unread);
/*     */     
/* 106 */     this.isInited = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 112 */     this.isInited = true;
/* 113 */     this.internalIn.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 119 */     this.isInited = true;
/* 120 */     return this.internalIn.read();
/*     */   }
/*     */ }


/* Location:              C:\Users\ennio\Downloads\HMMS_netwiser_coa_WS\WEB-INF\classes\!\com\hkhs\hmms\co\\util\UnicodeInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */