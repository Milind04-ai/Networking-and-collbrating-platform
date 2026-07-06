let selectedId =
    PROJECTS.length > 0
    ? PROJECTS[0].id
    : null;
    function renderCards(filter='all'){

    const c = document.getElementById('project-cards');
    c.innerHTML = '';
/*
    PROJECTS.forEach(p=>{

        const card = document.createElement('a');

        card.className =
            'project-card' +
            (p.id==selectedId ? ' selected' : '');

        card.href='#';
        card.dataset.id=p.id;

        card.innerHTML=`

        <div class="project-card-head">

            <div class="project-emoji">
                ${p.emoji}
            </div>

            <div>
                <div class="project-title">
                    ${p.title}
                </div>

                <div class="project-owner">
                    ${p.owner}
                </div>
            </div>

        </div>

        <div class="project-desc">
            ${p.desc}
        </div>

        <div class="skill-tags">
            ${p.skills.map(
                s=>`<span class="skill-tag">${s}</span>`
            ).join('')}
        </div>
        `;

        card.addEventListener('click',e=>{
            e.preventDefault();
            selectProject(p.id);
        });

        c.appendChild(card);
    });*/
}
function renderDetail(id){

    const p =
        PROJECTS.find(x=>x.id==id);

const applyBtn =
document.getElementById(
    "apply-btn"
);

if(p.ownerId === currentUserId){

    applyBtn.style.display =
        "none";

}else{

    applyBtn.style.display =
        "block";
}

    if(!p) return;
    
    document.getElementById(
    "projectIdField"
).value = p.id;

    document.getElementById('detail-emoji')
        .textContent = p.emoji;

    document.getElementById('detail-title')
        .textContent = p.title;

    document.getElementById('detail-owner')
        .textContent = p.owner;

    document.getElementById('detail-skills')
        .innerHTML =
        p.skills.map(
            s=>`<span class="skill-tag">${s}</span>`
        ).join('');

    document.getElementById(
    "detail-roles"
).innerHTML =
    p.rolesHtml;

    document.getElementById(
    "detail-team"
).innerHTML =
    p.teamHtml;
}function selectProject(id){selectedId=id;document.querySelectorAll('.project-card').forEach(c=>{c.classList.toggle('selected',parseInt(c.dataset.id)===id);});renderDetail(id);const panel=document.querySelector('.detail-panel');panel.style.animation='none';panel.offsetHeight;panel.style.animation='fadeIn 0.25s ease';}
document.addEventListener('DOMContentLoaded',()=>{renderSidebar('projects');loadNotificationCounts();renderTopbar('Projects');initRipples();initModals();initSearchShortcut();if(PROJECTS.length > 0){

    if(PROJECTS.length > 0){

    renderDetail(
        PROJECTS[0].id
    );

    selectProject(
        PROJECTS[0].id
    );
}
}
document.querySelectorAll('.filter-chip').forEach(chip=>{chip.addEventListener('click',()=>{document.querySelectorAll('.filter-chip').forEach(c=>c.classList.remove('active'));chip.classList.add('active');renderCards(chip.dataset.filter||'all');});});
document.getElementById('project-search')?.addEventListener('input',function(){const q=this.value.toLowerCase();document.querySelectorAll('.project-card').forEach(card=>{card.style.display=card.textContent.toLowerCase().includes(q)?'':'none';});});
});

document.querySelectorAll(
    '.project-card'
).forEach(card=>{

    card.addEventListener(
        'click',
        ()=>{

            selectProject(
                card.dataset.id
            );
        });
});
