package com.pattern.ASTmanager;

import ASTMCore.ASTMSemantics.AggregateScope;
import ASTMCore.ASTMSemantics.ProgramScope;
import ASTMCore.ASTMSource.CompilationUnit;
import ASTMCore.ASTMSyntax.DeclarationAndDefinition.*;
import ASTMCore.ASTMSyntax.Expression.*;
import ASTMCore.ASTMSyntax.Statement.*;
import ASTMCore.ASTMSyntax.Types.*;
import ASTMCore.visitor.GASTVisitor;

import java.util.ArrayList;
import java.util.Iterator;

public class JSONEncoder implements GASTVisitor {
  private String jsonClass;

  public JSONEncoder(){
    this.jsonClass = "";
  }

  public String getJsonClass() {
    return jsonClass;
  }

  @Override
  public void visitCompilationUnit(CompilationUnit compilationUnit) {
    jsonClass += "{\"package\": ";
    compilationUnit.getgPackage().accept(this);
    compilationUnit.getOpensScope().accept(this);
    jsonClass += "}";
  }

  @Override
  public void visitNameSpaceDefinition(NameSpaceDefinition nameSpaceDefinition) {
    nameSpaceDefinition.getNameSpace().accept(this);
  }

  @Override
  public void visitName(Name name) {
    jsonClass +="\""+name.getNameString()+"\",";
  }

  @Override
  public void visitProgramScope(ProgramScope programScope) {
    for (DefintionObject definitionObject : programScope.getDeclOrDefn()) {
      definitionObject.accept(this);
    }
  }

  @Override
  public void visitAggregateTypeDefinition(AggregateTypeDefinition aggregateTypeDefinition) {
    aggregateTypeDefinition.getAggregateType().accept(this);
  }

  @Override
  public void visitClassType(ClassType classType) {
    jsonClass += "\"className\": ";
    classType.getNameString().accept(this);
    jsonClass += "\"atributes\": [";
    for (MemberObject member : classType.getMembers()) {
      member.accept(this);
    }
    jsonClass += "],";
    jsonClass += "\"methods\": [";
    classType.getOpensScope().accept(this);
    jsonClass += "],";
  }
  @Override
  public void visitMemberObject(MemberObject memberObject) {
    jsonClass += "{ ";
    memberObject.getMember().accept(this);
    jsonClass += "\"modifier\": [";
    for (Modifiers modifier : memberObject.getModifiers()) {
      modifier.accept(this);
    }
    jsonClass += "],";
    jsonClass += "},";
  }

  @Override
  public void visitVariableDefinition(VariableDefinition variableDefinition) {
    Iterator<Fragment> fragmentIterator = variableDefinition.getFragments().iterator();
    if (fragmentIterator.hasNext()) {
      jsonClass += "\"name\": ";
      fragmentIterator.next().accept(this);
      jsonClass += "\"type\": ";
      variableDefinition.getDefinitionType().accept(this);
    }

  }

  @Override
  public void visitFragment(Fragment fragment) {
    fragment.getIdentifierName().accept(this);
  }

  @Override
  public void visitAggregateScope(AggregateScope aggregateScope) {
    for (DefintionObject definitionObject : aggregateScope.getDeclOrDefn()) {
      if (definitionObject != null) {
        definitionObject.accept(this);
      }
    }
  }

  @Override
  public void visitFunctionDefintion(FunctionDefintion functionDefintion) {
    jsonClass += "{";
    if (functionDefintion.getReturnType() != null){
      jsonClass += "\"returnType\": ";
      functionDefintion.getReturnType().accept(this);
    }else{
      //in case that the function is the constructor
      jsonClass += "\"returnType\": null,";
    }
    jsonClass += "\"arguments\": [";
    for (FormalParameterDefinition formalParameterDefinition: functionDefintion.getFormalParameters()){
      jsonClass += "{\"name\": ";
      formalParameterDefinition.getIdentifierName().accept(this);
      jsonClass += "\"type\": ";
      formalParameterDefinition.getDefinitionType().accept(this);
      jsonClass += "},";
    }
    jsonClass += "],";
    jsonClass += "\"modifier\": [";
    for (Modifiers modifier: functionDefintion.getModifiers()){
      modifier.accept(this);
    }
    jsonClass += "],";
    jsonClass += "\"name\": ";
    functionDefintion.getIdentifierName().accept(this);
    jsonClass += "},";

  }

  @Override
  public void visitNamedTypeReference(NamedTypeReference namedTypeReference) {
    namedTypeReference.getTypeName().accept(this);
  }

  @Override
  public void visitPublicModifier(PublicModifier publicModifier) {
    jsonClass += "\"" + publicModifier.getModifier() + "\",";
  }

  @Override
  public void visitAbstractModifier(AbstractModifier abstractModifier) {
    jsonClass += "\"" + abstractModifier.getModifier() + "\",";
  }

  @Override
  public void visitAnnotationModifier(AnnotationModifier annotationModifier) {
    jsonClass += "\"" + annotationModifier.getModifier() + "\",";
  }

  @Override
  public void visitDefaultModifier(DefaultModifier defaultModifier) {
    jsonClass += "\"" + defaultModifier.getModifier() + "\",";
  }

  @Override
  public void visitFinalModifier(FinalModifier finalModifier) {
    jsonClass += "\"" + finalModifier.getModifier() + "\",";
  }

  @Override
  public void visitNativeModifier(NativeModifier nativeModifier) {
    jsonClass += "\"" + nativeModifier.getModifier() + "\",";
  }

  @Override
  public void visitPrivateModifier(PrivateModifier privateModifier) {
    jsonClass += "\"" + privateModifier.getModifier() + "\",";
  }

  @Override
  public void visitProtectedModifier(ProtectedModifier protectedModifier) {
    jsonClass += "\"" + protectedModifier.getModifier() + "\",";
  }

  @Override
  public void visitStaticModifier(StaticModifier staticModifier) {
    jsonClass += "\"" + staticModifier.getModifier() + "\",";
  }

  @Override
  public void visitStrictfpModifier(StrictfpModifier strictfpModifier) {
    jsonClass += "\"" + strictfpModifier.getModifier() + "\",";
  }

  @Override
  public void visitSynchronizedModifier(SynchronizedModifier synchronizedModifier) {
    jsonClass += "\"" + synchronizedModifier.getModifier() + "\",";

  }

  @Override
  public void visitTransientModifier(TransientModifier transientModifier) {
    jsonClass += "\"" + transientModifier.getModifier() + "\",";
  }

  @Override
  public void visitVolatileModifier(VolatileModifier volatileModifier) {
    jsonClass += "\"" + volatileModifier.getModifier() + "\",";
  }

  @Override
  public void visitEnumTypeDefinition(EnumTypeDefinition enumTypeDefinition) { }

  @Override
  public void visitEnumLiteralDefinition(EnumLiteralDefinition enumLiteralDefinition) {
  }

  @Override
  public void visitEnumType(EnumType enumType) {

  }

  @Override
  public void visitReturnStatement(ReturnStatement returnStatement) {
  }

  @Override
  public void visitFormalParameterDefinition(FormalParameterDefinition formalParameterDefinition) {

  }

  @Override
  public void visitLabelDefinition(LabelDefinition labelDefinition) {

  }

  @Override
  public void visitImportDeclaration(ImportDeclaration importDeclaration) {

  }

  @Override
  public void visitDerivesFrom(DerivesFrom derivesFrom) {

  }

  @Override
  public void visitImplementsTo(ImplementsTo implementsTo) {

  }

  @Override
  public void visitParameterizedType(ParameterizedType parameterizedType) {

  }

  @Override
  public void visitArrayType(ArrayType arrayType) {

  }

  @Override
  public void visitTypeParameter(TypeParameter typeParameter) {

  }

  @Override
  public void visitBlockStatement(BlockStatement blockStatement) {

  }

  @Override
  public void visitLabeledStatement(LabeledStatement labeledStatement) {

  }

  @Override
  public void visitEmptyStatement(EmptyStatement emptyStatement) {

  }

  @Override
  public void visitIfStatement(IfStatement ifStatement) {

  }

  @Override
  public void visitWhileStatement(WhileStatement whileStatement) {

  }

  @Override
  public void visitDoWhileStatement(DoWhileStatement doWhileStatement) {

  }

  @Override
  public void visitContinueStatement(ContinueStatement continueStatement) {

  }

  @Override
  public void visitBreakStatement(BreakStatement breakStatement) {

  }

  @Override
  public void visitThrowStatement(ThrowStatement throwStatement) {

  }

  @Override
  public void visitExpressionStatement(ExpressionStatement expressionStatement) {

  }

  @Override
  public void visitForCheckAfterStatement(ForCheckAfterStatement forCheckAfterStatement) {

  }

  @Override
  public void visitForIterator(ForIterator forIterator) {

  }

  @Override
  public void visitTryStatement(TryStatement tryStatement) {

  }

  @Override
  public void visitCatchBlock(CatchBlock catchBlock) {

  }

  @Override
  public void visitSwitchStatement(SwitchStatement switchStatement) {

  }

  @Override
  public void visitSwitchCase(SwitchCase switchCase) {

  }

  @Override
  public void visitCaseBlock(CaseBlock caseBlock) {

  }

  @Override
  public void visitDefaultBlock(DefaultBlock defaultBlock) {

  }

  @Override
  public void visitAssertStatement(AssertStatement assertStatement) {

  }

  @Override
  public void visitSynchronizedStatement(SynchronizedStatement synchronizedStatement) {

  }

  @Override
  public void visitDeclarationOrDefinitionStatement(DeclarationOrDefinitionStatement declarationOrDefinitionStatement) {

  }

  @Override
  public void visitSuperInvocation(SuperInvocation superInvocation) {

  }

  @Override
  public void visitBinaryExpression(BinaryExpression binaryExpression) {

  }

  @Override
  public void visitMultiply(Multiply multiply) {

  }

  @Override
  public void visitModulus(Modulus modulus) {

  }

  @Override
  public void visitDivide(Divide divide) {

  }

  @Override
  public void visitAdd(Add add) {

  }

  @Override
  public void visitSubtract(Subtract subtract) {

  }

  @Override
  public void visitBitLeftShift(BitLeftShift bitLeftShift) {

  }

  @Override
  public void visitBitSignedRightShift(BitSignedRightShift bitSignedRightShift) {

  }

  @Override
  public void visitBitUnsignedRightShift(BitUnsignedRightShift bitUnsignedRightShift) {

  }

  @Override
  public void visitLess(Less less) {

  }

  @Override
  public void visitGreater(Greater greater) {

  }

  @Override
  public void visitNotGreater(NotGreater notGreater) {

  }

  @Override
  public void visitNotLess(NotLess notLess) {

  }

  @Override
  public void visitEqual(Equal equal) {

  }

  @Override
  public void visitNotEqual(NotEqual notEqual) {

  }

  @Override
  public void visitBitAnd(BitAnd bitAnd) {

  }

  @Override
  public void visitBitXor(BitXor bitXor) {

  }

  @Override
  public void visitBitOr(BitOr bitOr) {

  }

  @Override
  public void visitAnd(And and) {

  }

  @Override
  public void visitOr(Or or) {

  }

  @Override
  public void visitAssign(Assign assign) {

  }

  @Override
  public void visitAssignMultiply(AssignMultiply assignMultiply) {

  }

  @Override
  public void visitAssignDivide(AssignDivide assignDivide) {

  }

  @Override
  public void visitAssignModulus(AssignModulus assignModulus) {

  }

  @Override
  public void visitAssignAdd(AssignAdd assignAdd) {

  }

  @Override
  public void visitAssignSubtract(AssignSubtract assignSubtract) {

  }

  @Override
  public void visitAssignBitLeftShift(AssignBitLeftShift assignBitLeftShift) {

  }

  @Override
  public void visitAssignBitSignedRightShift(AssignBitSignedRightShift assignBitSignedRightShift) {

  }

  @Override
  public void visitAssignBitUnsignedRightShift(AssignBitUnsignedRightShift assignBitUnsignedRightShift) {

  }

  @Override
  public void visitAssignBitAnd(AssignBitAnd assignBitAnd) {

  }

  @Override
  public void visitAssignBitXor(AssignBitXor assignBitXor) {

  }

  @Override
  public void visitAssignBitOr(AssignBitOr assignBitOr) {

  }

  @Override
  public void visitGenericOperator(GenericOperator genericOperator) {

  }

  @Override
  public void visitInstanceOfExpression(InstanceOfExpression instanceOfExpression) {

  }

  @Override
  public void visitPrefixUnaryExpression(PrefixUnaryExpression prefixUnaryExpression) {

  }

  @Override
  public void visitPostfixUnaryExpression(PostfixUnaryExpression postfixUnaryExpression) {

  }

  @Override
  public void visitPostIncrement(PostIncrement postIncrement) {

  }

  @Override
  public void visitPostDecrement(PostDecrement postDecrement) {

  }

  @Override
  public void visitUnaryPlus(UnaryPlus unaryPlus) {

  }

  @Override
  public void visitUnaryMinus(UnaryMinus unaryMinus) {

  }

  @Override
  public void visitIncrement(Increment increment) {

  }

  @Override
  public void visitDecrement(Decrement decrement) {

  }

  @Override
  public void visitBitNot(BitNot bitNot) {

  }

  @Override
  public void visitNot(Not not) {

  }

  @Override
  public void visitDefaultUnaryOperator(DefaultUnaryOperator defaultUnaryOperator) {

  }

  @Override
  public void visitCastExpression(CastExpression castExpression) {

  }

  @Override
  public void visitBooleanLiteral(BooleanLiteral booleanLiteral) {
    jsonClass += "\""+booleanLiteral.getValue()+"\"";
  }

  @Override
  public void visitCharLiteral(CharLiteral charLiteral) {
    jsonClass += "\""+charLiteral.getValue()+"\"";
  }

  @Override
  public void visitIntegerLiteral(IntegerLiteral integerLiteral) {
    jsonClass += "\""+integerLiteral.getValue()+"\"";
  }

  @Override
  public void visitStringLiteral(StringLiteral stringLiteral) {
    jsonClass += "\""+stringLiteral.getValue()+"\"";
  }

  @Override
  public void visitRealLiteral(RealLiteral realLiteral) {
    jsonClass += "\""+realLiteral.getValue()+"\"";
  }

  @Override
  public void visitNullLiteral(NullLiteral nullLiteral) {
    jsonClass += "\""+nullLiteral.getValue()+"\"";
  }

  @Override
  public void visitIdentifierReference(IdentifierReference identifierReference) {

  }

  @Override
  public void visitParenthesizedExpression(ParenthesizedExpression parenthesizedExpression) {

  }

  @Override
  public void visitFunctionCallExpression(FunctionCallExpression functionCallExpression) {

  }

  @Override
  public void visitActualParameterExpression(ActualParameterExpression actualParameterExpression) {

  }

  @Override
  public void visitSuperMethodInvocation(SuperMethodInvocation superMethodInvocation) {

  }

  @Override
  public void visitArrayCreation(ArrayCreation arrayCreation) {

  }

  @Override
  public void visitCollectionExpression(CollectionExpression collectionExpression) {

  }

  @Override
  public void visitArrayAccess(ArrayAccess arrayAccess) {
  }

  @Override
  public void visitConditionalExpression(ConditionalExpression conditionalExpression) {

  }

  @Override
  public void visitNewExpression(NewExpression newExpression) {

  }

  @Override
  public void visitQualifiedOverData(QualifiedOverData qualifiedOverData) {

  }

  @Override
  public void visitLabelAccess(LabelAccess labelAccess) {
  }

  @Override
  public void visitMemberAccess(MemberAccess memberAccess) {
  }

  @Override
  public void visitSuperMemberAccess(SuperMemberAccess superMemberAccess) {

  }

  @Override
  public void visitVariableExpression(VariableExpression variableExpression) {

  }



  @Override
  public void visitSingleMemberAnnotation(SingleMemberAnnotation singleMemberAnnotation) {

  }


}
