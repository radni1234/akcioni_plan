import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKriterijum } from 'app/shared/model/kriterijum.model';
import { KriterijumService } from './kriterijum.service';

@Component({
    selector: 'jhi-kriterijum-delete-dialog',
    templateUrl: './kriterijum-delete-dialog.component.html'
})
export class KriterijumDeleteDialogComponent {
    kriterijum: IKriterijum;

    constructor(private kriterijumService: KriterijumService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kriterijumService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'kriterijumListModification',
                content: 'Deleted an kriterijum'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kriterijum-delete-popup',
    template: ''
})
export class KriterijumDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kriterijum }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KriterijumDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.kriterijum = kriterijum;
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
