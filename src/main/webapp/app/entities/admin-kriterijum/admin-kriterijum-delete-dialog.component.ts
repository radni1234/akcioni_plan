import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdminKriterijum } from 'app/shared/model/admin-kriterijum.model';
import { AdminKriterijumService } from './admin-kriterijum.service';

@Component({
    selector: 'jhi-admin-kriterijum-delete-dialog',
    templateUrl: './admin-kriterijum-delete-dialog.component.html'
})
export class AdminKriterijumDeleteDialogComponent {
    adminKriterijum: IAdminKriterijum;

    constructor(
        private adminKriterijumService: AdminKriterijumService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adminKriterijumService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adminKriterijumListModification',
                content: 'Deleted an adminKriterijum'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-admin-kriterijum-delete-popup',
    template: ''
})
export class AdminKriterijumDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adminKriterijum }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdminKriterijumDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adminKriterijum = adminKriterijum;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
