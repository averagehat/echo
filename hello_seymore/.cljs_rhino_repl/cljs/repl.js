// Compiled by ClojureScript 1.7.122 {}
goog.provide('cljs.repl');
goog.require('cljs.core');
cljs.repl.print_doc = (function cljs$repl$print_doc(m){
cljs.core.println.call(null,"-------------------------");

cljs.core.println.call(null,[cljs.core.str((function (){var temp__4425__auto__ = new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(temp__4425__auto__)){
var ns = temp__4425__auto__;
return [cljs.core.str(ns),cljs.core.str("/")].join('');
} else {
return null;
}
})()),cljs.core.str(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Protocol");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m))){
var seq__10697_10711 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__10698_10712 = null;
var count__10699_10713 = (0);
var i__10700_10714 = (0);
while(true){
if((i__10700_10714 < count__10699_10713)){
var f_10715 = cljs.core._nth.call(null,chunk__10698_10712,i__10700_10714);
cljs.core.println.call(null,"  ",f_10715);

var G__10716 = seq__10697_10711;
var G__10717 = chunk__10698_10712;
var G__10718 = count__10699_10713;
var G__10719 = (i__10700_10714 + (1));
seq__10697_10711 = G__10716;
chunk__10698_10712 = G__10717;
count__10699_10713 = G__10718;
i__10700_10714 = G__10719;
continue;
} else {
var temp__4425__auto___10720 = cljs.core.seq.call(null,seq__10697_10711);
if(temp__4425__auto___10720){
var seq__10697_10721__$1 = temp__4425__auto___10720;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__10697_10721__$1)){
var c__10344__auto___10722 = cljs.core.chunk_first.call(null,seq__10697_10721__$1);
var G__10723 = cljs.core.chunk_rest.call(null,seq__10697_10721__$1);
var G__10724 = c__10344__auto___10722;
var G__10725 = cljs.core.count.call(null,c__10344__auto___10722);
var G__10726 = (0);
seq__10697_10711 = G__10723;
chunk__10698_10712 = G__10724;
count__10699_10713 = G__10725;
i__10700_10714 = G__10726;
continue;
} else {
var f_10727 = cljs.core.first.call(null,seq__10697_10721__$1);
cljs.core.println.call(null,"  ",f_10727);

var G__10728 = cljs.core.next.call(null,seq__10697_10721__$1);
var G__10729 = null;
var G__10730 = (0);
var G__10731 = (0);
seq__10697_10711 = G__10728;
chunk__10698_10712 = G__10729;
count__10699_10713 = G__10730;
i__10700_10714 = G__10731;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_10732 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__9541__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__9541__auto__)){
return or__9541__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.call(null,arglists_10732);
} else {
cljs.core.prn.call(null,((cljs.core._EQ_.call(null,new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first.call(null,arglists_10732)))?cljs.core.second.call(null,arglists_10732):arglists_10732));
}
} else {
}
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"special-form","special-form",-1326536374).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Special Form");

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.contains_QMARK_.call(null,m,new cljs.core.Keyword(null,"url","url",276297046))){
if(cljs.core.truth_(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))){
return cljs.core.println.call(null,[cljs.core.str("\n  Please see http://clojure.org/"),cljs.core.str(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))].join(''));
} else {
return null;
}
} else {
return cljs.core.println.call(null,[cljs.core.str("\n  Please see http://clojure.org/special_forms#"),cljs.core.str(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Macro");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"REPL Special Function");
} else {
}

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
var seq__10701 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__10702 = null;
var count__10703 = (0);
var i__10704 = (0);
while(true){
if((i__10704 < count__10703)){
var vec__10705 = cljs.core._nth.call(null,chunk__10702,i__10704);
var name = cljs.core.nth.call(null,vec__10705,(0),null);
var map__10706 = cljs.core.nth.call(null,vec__10705,(1),null);
var map__10706__$1 = ((((!((map__10706 == null)))?((((map__10706.cljs$lang$protocol_mask$partition0$ & (64))) || (map__10706.cljs$core$ISeq$))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__10706):map__10706);
var doc = cljs.core.get.call(null,map__10706__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists = cljs.core.get.call(null,map__10706__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name);

cljs.core.println.call(null," ",arglists);

if(cljs.core.truth_(doc)){
cljs.core.println.call(null," ",doc);
} else {
}

var G__10733 = seq__10701;
var G__10734 = chunk__10702;
var G__10735 = count__10703;
var G__10736 = (i__10704 + (1));
seq__10701 = G__10733;
chunk__10702 = G__10734;
count__10703 = G__10735;
i__10704 = G__10736;
continue;
} else {
var temp__4425__auto__ = cljs.core.seq.call(null,seq__10701);
if(temp__4425__auto__){
var seq__10701__$1 = temp__4425__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__10701__$1)){
var c__10344__auto__ = cljs.core.chunk_first.call(null,seq__10701__$1);
var G__10737 = cljs.core.chunk_rest.call(null,seq__10701__$1);
var G__10738 = c__10344__auto__;
var G__10739 = cljs.core.count.call(null,c__10344__auto__);
var G__10740 = (0);
seq__10701 = G__10737;
chunk__10702 = G__10738;
count__10703 = G__10739;
i__10704 = G__10740;
continue;
} else {
var vec__10708 = cljs.core.first.call(null,seq__10701__$1);
var name = cljs.core.nth.call(null,vec__10708,(0),null);
var map__10709 = cljs.core.nth.call(null,vec__10708,(1),null);
var map__10709__$1 = ((((!((map__10709 == null)))?((((map__10709.cljs$lang$protocol_mask$partition0$ & (64))) || (map__10709.cljs$core$ISeq$))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__10709):map__10709);
var doc = cljs.core.get.call(null,map__10709__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists = cljs.core.get.call(null,map__10709__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name);

cljs.core.println.call(null," ",arglists);

if(cljs.core.truth_(doc)){
cljs.core.println.call(null," ",doc);
} else {
}

var G__10741 = cljs.core.next.call(null,seq__10701__$1);
var G__10742 = null;
var G__10743 = (0);
var G__10744 = (0);
seq__10701 = G__10741;
chunk__10702 = G__10742;
count__10703 = G__10743;
i__10704 = G__10744;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
}
});

//# sourceMappingURL=repl.js.map