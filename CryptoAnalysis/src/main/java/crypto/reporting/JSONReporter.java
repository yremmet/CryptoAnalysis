package crypto.reporting;

import boomerang.WeightedBoomerang;
import boomerang.jimple.Statement;
import boomerang.jimple.Val;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import crypto.analysis.AnalysisSeedWithSpecification;
import crypto.analysis.EnsuredCryptSLPredicate;
import crypto.analysis.IAnalysisSeed;
import crypto.analysis.ICrySLResultsListener;
import crypto.analysis.errors.AbstractError;
import crypto.interfaces.ISLConstraint;
import crypto.rules.CryptSLPredicate;
import crypto.typestate.CallSiteWithParamIndex;
import sync.pds.solver.nodes.Node;
import typestate.TransitionFunction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONReporter implements ICrySLResultsListener {

	private static ObjectMapper mapper = new ObjectMapper();

	private static final String LABEL ="CryptoAnalysis";

	private static final String FILE_PREFIX = "Issue_";

	@Override
	public void reportError(AbstractError error) {
		Issue issue = makeIssueFromError(error);

		String fileName = FILE_PREFIX + issue.hash;
		File reportFile = new File(fileName);

		try(FileWriter writer = new FileWriter(reportFile, true)) {
			writer.write(issue.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Issue makeIssueFromError(AbstractError error){
		int issueHash = Objects.hash(error.getErrorLocation(), error.getRule());
		String title = error.getErrorLocation().toString() + error.getRule().getClassName();
		String explanation = error.getRule().toString();
		ArrayList<String> labels = new ArrayList<>();
		labels.add(LABEL);
		return new Issue(issueHash, title, explanation, labels);
	}

	@Override
	public void ensuredPredicates(Table<Statement, Val, Set<EnsuredCryptSLPredicate>> existingPredicates, Table<Statement, IAnalysisSeed, Set<CryptSLPredicate>> expectedPredicates, Table<Statement, IAnalysisSeed, Set<CryptSLPredicate>> missingPredicates) {

	}

	@Override
	public void predicateContradiction(Node<Statement, Val> node, Entry<CryptSLPredicate, CryptSLPredicate> disPair) {

	}

	@Override
	public void checkedConstraints(AnalysisSeedWithSpecification analysisSeedWithSpecification, Collection<ISLConstraint> relConstraints) {

	}

	@Override
	public void onSeedTimeout(Node<Statement, Val> seed) {

	}

	@Override
	public void onSeedFinished(IAnalysisSeed seed, WeightedBoomerang<TransitionFunction> solver) {

	}

	@Override
	public void collectedValues(AnalysisSeedWithSpecification seed, Multimap<CallSiteWithParamIndex, Statement> collectedValues) {

	}

	@Override
	public void discoveredSeed(IAnalysisSeed curr) {

	}

	@Override
	public void unevaluableConstraint(AnalysisSeedWithSpecification seed, ISLConstraint con, Statement location) {

	}
	/**
	 * Class for a single issue, includes converting the issue to json
	 */
	public class Issue {

		private int hash;
		private String title;
		/**
		 * Explanation of the problem. Should include the line, so that
		 * multiple occurrences of the same problem can be distinguished.
		 */
		private String body;
		private List<String> labels;

		public Issue(int hash, String title, String body, List<String> labels) {
			this.hash = hash;
			this.title = title;
			this.body = body;
			this.labels = labels;
		}

		public String getJsonString(){
			String jsonInString = null;
			try {
				jsonInString = mapper.writeValueAsString(this);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return jsonInString;
		}
	}

}
