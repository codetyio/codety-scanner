package io.codety.scanner.analyzer.golangcilint;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GolangcilintModuleUtil {

    public static List<File> findGoModules(String localGitRepoPath) {
        File file = new File(localGitRepoPath);
        List<File> result = new ArrayList<>();
        Queue<File> q = new LinkedList();
        q.add(file);
        while(!q.isEmpty()){

            int size = q.size();
            for(int i=0; i<size; i++){
                File curr = q.poll();
                boolean isGoModulePath = false;
                List<File> allChildrenFolders = new ArrayList<>();
                for (File child : curr.listFiles()) {
                    if (child.isFile() && child.getName().equals("go.mod")) {
                        isGoModulePath = true;
                        break;
                    }
                    if(child.isDirectory()){
                        allChildrenFolders.add(child);
                    }
                }

                if(isGoModulePath){
                    result.add(curr);
                }else{
                    q.addAll(allChildrenFolders);
                }
            }

        }


        return result;
    }

}
