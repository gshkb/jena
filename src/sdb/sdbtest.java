/*
 * (c) Copyright 2006 Hewlett--Packard Development Company, LP
 * [See end of file]
 */

package sdb;

import java.util.List;

import junit.framework.TestSuite;
import sdb.cmd.CmdArgsDB;

import arq.cmd.CmdException;
import arq.cmd.TerminateException;

import com.hp.hpl.jena.query.junit.SimpleTestRunner;
import com.hp.hpl.jena.sdb.engine.QueryCompilerBase;
import com.hp.hpl.jena.sdb.junit.QueryTestSDBFactory;
 
 /** Run a test suite
  * 
  *  <p>
  *  Usage:<pre>
  *  jena.sdbtest [db spec] [ manifest ]
  *  </pre>
  *  </p>
  * 
  * @author Andy Seaborne
  * @version $Id: sdbtest.java,v 1.9 2006/04/22 19:51:11 andy_seaborne Exp $
  */ 
 
public class sdbtest extends CmdArgsDB
{
    public static final String usage = "sdbtest <SPEC> --schema schemaName [--direct] [manifest]" ;
    
    public static void main (String [] argv)
    {
        try { main2(argv) ; }
        catch (CmdException ex)
        {
            System.err.println(ex.getMessage()) ;
            if ( ex.getCause() != null )
                ex.getCause().printStackTrace(System.err) ;
        }
        catch (TerminateException ex) { System.exit(ex.getCode()) ; }
    }

    public static void main2(String[] args)
    {
        sdbtest cmd = new sdbtest(args);
        cmd.process() ;
        cmd.exec();
    }
    
    String filename = null ;

    protected sdbtest(String[] args)
    {
        super("sdbtest", args);
    }

    @Override
    protected void addCmdUsage(List<String> acc) { acc.add(usage) ; }

    @Override
    protected void checkCommandLine()
    { }
    
    // Don't use exec1 which has a transaction wrapper.
    
    @Override 
    protected void execWithArgs()
    {
        for ( Object x : getPositional() )
        {
            execOneManifest((String)x) ;
        }
    }
    
    
    @Override
    protected void exec0()
    {
        execOneManifest("testing/manifest-sdb.ttl") ;
    }

    @Override
    protected boolean exec1(String manifest)
    { return false ; }

    private void execOneManifest(String manifest)
    { 
        if ( verbose )
        {
            //SchemaBase.printBlock = true ;
            //SchemaBase.printAbstractSQL = true ;
            QueryCompilerBase.printSQL = true ;
            System.out.println("Manifest: "+manifest) ;
        }
        TestSuite ts = new TestSuite() ;
        ts.addTest(QueryTestSDBFactory.make(getStore(), manifest)) ;
        SimpleTestRunner.runAndReport(ts) ;
    }
}
 


/*
 * (c) Copyright 2003, 2004, 2005 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
