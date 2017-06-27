package com.sdjzu.xg14.glmisattendanceandroid.addAttendance;

import android.annotation.SuppressLint;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.sdjzu.xg14.glmisattendanceandroid.R.id.textView;

/**
 * Created on 30/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */
public class EmployeeAdapter extends SectioningAdapter {

    private List<Employee> mEmployees = new ArrayList<>();
    private List<String> mDepartments = new ArrayList<>();
    private List<Integer> mPositions = new ArrayList<>();
    private EmployeeAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, String name);

    }

    public void setOnItemClickListener(EmployeeAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    private class Section {
        int index;
        String header;
        ArrayList<String> items = new ArrayList<>();
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView textView;
        View view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.whole);
            textView = (TextView) itemView.findViewById(R.id.textView);
            if (mOnItemClickListener != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.
                                onItemClick(view, textView.getText().toString());
                        remove(getSection(), getPositionInSection());
                    }
                });
            }
        }
    }


    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder implements View.OnClickListener {
        TextView departmentName;
        TextView numOfDepartment;
        ImageButton collapseButton;
        View view;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            departmentName = (TextView) itemView.findViewById(textView);
            numOfDepartment = (TextView) itemView.findViewById(R.id.adapterPositionTextView);
            view = itemView.findViewById(R.id.whole);
            view.setOnClickListener(this);
            collapseButton = (ImageButton) itemView.findViewById(R.id.collapseButton);
            collapseButton.setOnClickListener(this);
        }

        void updateSectionCollapseToggle(boolean sectionIsCollapsed) {
            @DrawableRes int id = sectionIsCollapsed
                    ? R.drawable.ic_expand_more_black_24dp
                    : R.drawable.ic_expand_less_black_24dp;

            collapseButton.setImageDrawable(ContextCompat.getDrawable(collapseButton.getContext(), id));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.whole:
                case R.id.collapseButton:
                    int position = getAdapterPosition();
                    final int section = EmployeeAdapter.this.getSectionForAdapterPosition(position);
                    EmployeeAdapter.this.onToggleSectionCollapse(section);
                    updateSectionCollapseToggle(EmployeeAdapter.this.isSectionCollapsed(section));
                    break;
            }
        }
    }

    ArrayList<Section> sections = new ArrayList<>();


    public EmployeeAdapter() {

    }


    /**
     * 将每个部门的分界的position保存起来
     *
     * @param departments
     */
    private void createPositions(List<String> departments) {
        for (int i = 0; i < departments.size(); i++) {
            int position = getPositionForSection(departments.get(i));
            if (position != -1) {
                mPositions.add(position);
            }
        }
    }

    /**
     * 将每个分组的数据保存
     *
     * @param departments
     */
    private void createSection(List<String> departments) {
        for (int i = 0; i < departments.size(); i++) {
            appendSection(i, getNumberForSection(departments.get(i)));
        }
    }

    /**
     * @param index
     * @param itemCount 每个分组中item的个数
     */
    void appendSection(int index, int itemCount) {
        Section section = new Section();
        section.index = index;
        section.header = Integer.toString(index);

        if (itemCount != 0) {
            for (int j = mPositions.get(index); j < mPositions.get(index) + itemCount; j++) {
                section.items.add(mEmployees.get(j).getName());
            }
            sections.add(section);
        }
    }

    void onToggleSectionCollapse(int sectionIndex) {
        setSectionIsCollapsed(sectionIndex, !isSectionCollapsed(sectionIndex));
    }


    @Override
    public int getSectionHeaderUserType(int sectionIndex) {
        return sectionIndex;
    }

    /**
     * 获取department首次出现位置
     */
    public int getPositionForSection(String department) {
        for (int i = 0; i < mEmployees.size(); i++) {
            String sortStr = mEmployees.get(i).getDepartment();
            if (department.equals(sortStr)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取具体department的员工个数
     *
     * @param department
     * @return 具体department中员工数
     */
    public int getNumberForSection(String department) {
        int count = 0;
        for (int i = 0; i < mEmployees.size(); i++) {
            String sortStr = mEmployees.get(i).getDepartment();
            if (department.equals(sortStr)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getNumberOfSections() {
        return mDepartments.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).items.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return !TextUtils.isEmpty(sections.get(sectionIndex).header);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_simple_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_simple_header, parent, false);
        return new HeaderViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        final ItemViewHolder ivh = (ItemViewHolder) viewHolder;
        ivh.textView.setText(sections.get(sectionIndex).items.get(itemIndex));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.numOfDepartment.setText(Integer.toString(getNumberOfItemsInSection(sectionIndex)));
        hvh.departmentName.setText(mDepartments.get(headerType));
        hvh.updateSectionCollapseToggle(isSectionCollapsed(sectionIndex));
    }

    /**
     * 绑定数据源
     * @param employees
     * @param departments
     */
    public void addList(List<Employee> employees, List<String> departments) {
        mEmployees.clear();
        mEmployees.addAll(employees);
        mDepartments.clear();
        mDepartments.addAll(departments);
        mPositions.clear();
        sections.clear();
        notifyDataSetChanged();
        createPositions(departments);
        createSection(departments);
    }

    public void remove(Employee employee) {
        mEmployees.remove(employee);
        notifyAllSectionsDataSetChanged();
    }

    private void remove(int section, int positionInSection) {
        Section s = sections.get(section);
        s.items.remove(positionInSection);
        notifySectionItemRemoved(section, positionInSection);
    }
}
