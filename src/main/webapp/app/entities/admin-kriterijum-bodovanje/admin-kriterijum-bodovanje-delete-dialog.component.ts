import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';
import { AdminKriterijumBodovanjeService } from './admin-kriterijum-bodovanje.service';

@Component({
    selector: 'jhi-admin-kriterijum-bodovanje-delete-dialog',
    templateUrl: './admin-kriterijum-bodovanje-delete-dialog.component.html'
})
export class AdminKriterijumBodovanjeDeleteDialogComponent {
    adminKriterijumBodovanje: IAdminKriterijumBodovanje;

    constructor(
        private adminKriterijumBodovanjeService: AdminKriterijumBodovanjeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adminKriterijumBodovanjeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adminKriterijumBodovanjeListModification',
                content: 'Deleted an adminKriterijumBodovanje'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-admin-kriterijum-bodovanje-delete-popup',
    template: ''
})
export class AdminKriterijumBodovanjeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adminKriterijumBodovanje }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdminKriterijumBodovanjeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adminKriterijumBodovanje = adminKriterijumBodovanje;
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
