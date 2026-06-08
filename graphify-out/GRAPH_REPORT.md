# Graph Report - .  (2026-06-03)

## Corpus Check
- Corpus is ~9,931 words - fits in a single context window. You may not need a graph.

## Summary
- 142 nodes · 235 edges · 20 communities (7 shown, 13 thin omitted)
- Extraction: 92% EXTRACTED · 8% INFERRED · 0% AMBIGUOUS · INFERRED: 19 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 13|Community 13]]
- [[_COMMUNITY_Community 14|Community 14]]
- [[_COMMUNITY_Community 15|Community 15]]
- [[_COMMUNITY_Community 16|Community 16]]
- [[_COMMUNITY_Community 17|Community 17]]
- [[_COMMUNITY_Community 18|Community 18]]
- [[_COMMUNITY_Community 19|Community 19]]

## God Nodes (most connected - your core abstractions)
1. `RequestsLimitFilter` - 9 edges
2. `Util` - 9 edges
3. `PrimeApplicationTests` - 8 edges
4. `CallsTest` - 7 edges
5. `PrimesController` - 6 edges
6. `CribaRequest` - 6 edges
7. `PrimesServiceImpl` - 6 edges
8. `Test` - 6 edges
9. `DisplayName` - 6 edges
10. `Test` - 6 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Import Cycles
- None detected.

## Communities (20 total, 13 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.17
Nodes (13): HashMap, PrimesServiceImpl, Point, PrimesService, ChartResponse, DiffResponse, InverseConstantResponse, Override (+5 more)

### Community 1 - "Community 1"
Cohesion: 0.14
Nodes (14): RequestsLimitFilter, Filter, FilterChain, GlobalRequest, GlobalRequestRepo, HttpServletRequest, GlobalRequestRepo, UserRequestRepo (+6 more)

### Community 2 - "Community 2"
Cohesion: 0.27
Nodes (7): BigDecimal, Integer, List, String, Test, AproximacionInverso, AproximacionInversoTest

### Community 3 - "Community 3"
Cohesion: 0.30
Nodes (5): PerformanceResult, PrimeApplicationTests, PerformanceResult, DisplayName, Test

### Community 4 - "Community 4"
Cohesion: 0.38
Nodes (4): BeforeEach, CallsTest, DisplayName, Test

### Community 5 - "Community 5"
Cohesion: 0.42
Nodes (5): PrimesController, CribaRequest, PostMapping, ResponseEntity, PrimesService

### Community 6 - "Community 6"
Cohesion: 0.29
Nodes (5): PrimesService, ChartResponse, DiffResponse, InverseConstantResponse, PolarResponse

## Knowledge Gaps
- **16 isolated node(s):** `java.configuration.updateBuildConfiguration`, `String`, `Override`, `ChartResponse`, `CribaRequest` (+11 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **13 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `UserRequestRepo` connect `Community 1` to `Community 4`?**
  _High betweenness centrality (0.052) - this node is a cross-community bridge._
- **Why does `CribaRequest` connect `Community 5` to `Community 4`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **What connects `java.configuration.updateBuildConfiguration`, `String`, `Override` to the rest of the system?**
  _16 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.14492753623188406 - nodes in this community are weakly interconnected._