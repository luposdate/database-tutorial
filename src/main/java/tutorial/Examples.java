/**
 * Copyright (c) 2013, Institute of Information Systems (Sven Groppe and Lifan Qi), University of Luebeck
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 	- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * 	  disclaimer.
 * 	- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * 	  following disclaimer in the documentation and/or other materials provided with the distribution.
 * 	- Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
 * 	  products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tutorial;

/**
 * This is the class for all the examples for the applet
 *
 * @author Lifan Qi
 * @version 1.0
 *
 * */
public class Examples {
	static final String[] egStrings = {"Examples", "SQL", "Relational Algebra", "Tuple Calculus", "Domain Calculus"};
	static final String sql = "SELECT * FROM zertifiziert;";
	static final String ra = "πname(ρx(πpersonalnummer, name(Mitarbeiter))⋈{x.personalnummer=zertifiziert.personalnummer}Zertifiziert);";
	static final String tc = "{x[1]|Flug(x)Λ∃y(Flug(y)Λx[2]='Hamburg'Λy[3]='Pisa'Λx[3]=y[2]Λx[6]<y[5])};";
	static final String dc = "{x1|∃x2, x3, x4, x5, x6(Flug(x1, x2, x3, x4, x5, x6)Λ∃y1, y2, y3, y4, y5, y6(Flug(y1, y2, y3, y4, y5, y6)Λx2='Hamburg'Λy3='Pisa'Λx3=y2Λx6<y5))};";
}
