import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';
import { ProjekatBodovanjeService } from './projekat-bodovanje.service';

@Component({
    selector: 'jhi-projekat-bodovanje-delete-dialog',
    templateUrl: './projekat-bodovanje-delete-dialog.component.html'
})
export class ProjekatBodovanjeDeleteDialogComponent {
    projekatBodovanje: IProjekatBodovanje;

    constructor(
        private projekatBodovanjeService: ProjekatBodovanjeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projekatBodovanjeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'projekatBodovanjeListModification',
                content: 'Deleted an projekatBodovanje'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-projekat-bodovanje-delete-popup',
    template: ''
})
export class ProjekatBodovanjeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projekatBodovanje }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProjekatBodovanjeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.projekatBodovanje = projekatBodovanje;
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
