/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatBodovanjeDetailComponent } from 'app/entities/projekat-bodovanje/projekat-bodovanje-detail.component';
import { ProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';

describe('Component Tests', () => {
    describe('ProjekatBodovanje Management Detail Component', () => {
        let comp: ProjekatBodovanjeDetailComponent;
        let fixture: ComponentFixture<ProjekatBodovanjeDetailComponent>;
        const route = ({ data: of({ projekatBodovanje: new ProjekatBodovanje(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatBodovanjeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjekatBodovanjeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjekatBodovanjeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.projekatBodovanje).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
