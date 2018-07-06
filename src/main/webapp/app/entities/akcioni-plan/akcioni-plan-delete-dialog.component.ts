import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAkcioniPlan } from 'app/shared/model/akcioni-plan.model';
import { AkcioniPlanService } from './akcioni-plan.service';

@Component({
    selector: 'jhi-akcioni-plan-delete-dialog',
    templateUrl: './akcioni-plan-delete-dialog.component.html'
})
export class AkcioniPlanDeleteDialogComponent {
    akcioniPlan: IAkcioniPlan;

    constructor(
        private akcioniPlanService: AkcioniPlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.akcioniPlanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'akcioniPlanListModification',
                content: 'Deleted an akcioniPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-akcioni-plan-delete-popup',
    template: ''
})
export class AkcioniPlanDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ akcioniPlan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AkcioniPlanDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.akcioniPlan = akcioniPlan;
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
