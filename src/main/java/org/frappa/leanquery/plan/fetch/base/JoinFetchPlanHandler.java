package org.frappa.leanquery.plan.fetch.base;

import org.antlr.v4.runtime.misc.Pair;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.Table;

import javax.swing.*;
import java.util.List;

public interface JoinFetchPlanHandler<T extends FetchPlan> {
    Pair<List<SelectField<?>>,Table<?>> handleJoinFetches(List<SelectField<?>> rootFields, Table<?> rootTable, T plan);
}
