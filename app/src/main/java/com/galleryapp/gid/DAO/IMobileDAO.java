package com.galleryapp.gid.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by noverio.joe on 3/23/17.
 */

public interface IMobileDAO {

    public boolean InsertData(Object Data);
    public boolean UpdateData(Object InsertedData, Object DeletedData);
    public boolean DeleteData(Object DeletedData);
    public List<Objects> GetListData(Objects Data);
}
