import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';
import { KriterijumBodovanjeService } from './kriterijum-bodovanje.service';

@Component({
    selector: 'jhi-kriterijum-bodovanje-delete-dialog',
    templateUrl: './kriterijum-bodovanje-delete-dialog.component.html'
})
export class KriterijumBodovanjeDeleteDialogComponent {
    kriterijumBodovanje: IKriterijumBodovanje;

    constructor(
        private kriterijumBodovanjeService: KriterijumBodovanjeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kriterijumBodovanjeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'kriterijumBodovanjeListModification',
                content: 'Deleted an kriterijumBodovanje'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kriterijum-bodovanje-delete-popup',
    template: ''
})
export class KriterijumBodovanjeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kriterijumBodovanje }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KriterijumBodovanjeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.kriterijumBodovanje = kriterijumBodovanje;
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
